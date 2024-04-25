package org.flexi.app.presentation.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.repository.Repository
import org.flexi.app.domain.usecase.ResultState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _login = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val login: StateFlow<ResultState<String>> = _login.asStateFlow()

    private val _signup = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val signup = _signup.asStateFlow()

    private val _products = MutableStateFlow<ResultState<List<Products>>>(ResultState.Loading)
    val products: StateFlow<ResultState<List<Products>>> = _products.asStateFlow()

    private val _promotions =
        MutableStateFlow<ResultState<List<PromotionsProductsItem>>>(ResultState.Loading)
    val promotions: StateFlow<ResultState<List<PromotionsProductsItem>>> = _promotions.asStateFlow()

    private val _categories = MutableStateFlow<ResultState<List<Categories>>>(ResultState.Loading)
    val categories: StateFlow<ResultState<List<Categories>>> = _categories.asStateFlow()

    private val _books = MutableStateFlow<ResultState<List<BooksItem>>>(ResultState.Loading)
    val books: StateFlow<ResultState<List<BooksItem>>> = _books.asStateFlow()

    private val _carts = MutableStateFlow<ResultState<List<CartItem>>>(ResultState.Loading)
    val carts: StateFlow<ResultState<List<CartItem>>> = _carts.asStateFlow()

    private val _productItem = MutableStateFlow<ResultState<List<Products>>>(ResultState.Loading)
    val productItem: StateFlow<ResultState<List<Products>>> = _productItem.asStateFlow()

    private val _addToCart = MutableStateFlow<ResultState<CartItem>>(ResultState.Loading)
    val addToCart: StateFlow<ResultState<CartItem>> = _addToCart.asStateFlow()

    private val _cartItem = MutableStateFlow<ResultState<CartItem>>(ResultState.Loading)
    val cartItem: StateFlow<ResultState<CartItem>> = _cartItem.asStateFlow()
    fun getCartItemById(cartId: Long) {
        viewModelScope.launch {
            _cartItem.value = ResultState.Loading
            try {
                val response = repository.getCartItem(cartId)
                _cartItem.value = ResultState.Success(response)
            } catch (e: Exception) {
                _cartItem.value = ResultState.Error(e)
            }
        }
    }

    fun addToCartItem(productId: Long, quantity: Int, userId: Long) {
        viewModelScope.launch {
            _addToCart.value = ResultState.Loading
            try {
                val response = repository.addToCart(productId, quantity, userId)
                _addToCart.value = ResultState.Success(response)
            } catch (e: Exception) {
                _addToCart.value = ResultState.Error(e)
            }
        }
    }

    fun getProductById(id: List<Long>) {
        viewModelScope.launch {
            _productItem.value = ResultState.Loading
            try {
                val response = repository.getProductById(id)
                _productItem.value = ResultState.Success(response)
            } catch (e: Exception) {
                _productItem.value = ResultState.Error(e)
            }
        }
    }

    fun getCartsList(userId: Long) {
        viewModelScope.launch {
            _carts.value = ResultState.Loading
            try {
                val response = repository.getCartListByUserId(userId)
                _carts.value = ResultState.Success(response)
            } catch (e: Exception) {
                _carts.value = ResultState.Error(e)
            }
        }
    }

    fun getBooksList() {
        viewModelScope.launch {
            _books.value = ResultState.Loading
            try {
                val response = repository.getBooksList()
                _books.value = ResultState.Success(response)
            } catch (e: Exception) {
                _books.value = ResultState.Error(e)
            }
        }
    }

    fun getCategoriesList() {
        viewModelScope.launch {
            _categories.value = ResultState.Loading
            try {
                val response = repository.getCategories()
                _categories.value = ResultState.Success(response)
            } catch (e: Exception) {
                _categories.value = ResultState.Error(e)
            }
        }
    }

    fun getPromotionsItems() {
        viewModelScope.launch {
            _promotions.value = ResultState.Loading
            try {
                val response = repository.getPromotionsProducts()
                _promotions.value = ResultState.Success(response)
            } catch (e: Exception) {
                _promotions.value = ResultState.Error(e)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                _login.value = ResultState.Success(response)
            } catch (e: Exception) {
                _login.value = ResultState.Error(e)
            }
        }
    }

    fun signupUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.signupUser(username, email, password)
                _signup.value = ResultState.Success(response)
            } catch (e: Exception) {
                _signup.value = ResultState.Error(e)
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            _products.value = ResultState.Loading
            try {
                val response = repository.getProducts()
                _products.value = ResultState.Success(response)
            } catch (e: Exception) {
                _products.value = ResultState.Error(e)
            }
        }
    }

}