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
import org.flexi.app.domain.model.user.User
import org.flexi.app.domain.repository.Repository
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.screens.payment.model.Order
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

    private val _deleteItem = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val deleteItem: StateFlow<ResultState<Boolean>> = _deleteItem.asStateFlow()

    private val _userData = MutableStateFlow<ResultState<User>>(ResultState.Loading)
    val userData: StateFlow<ResultState<User>> = _userData.asStateFlow()

    private val _updateAddress = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val updateAddress: StateFlow<ResultState<Boolean>> = _updateAddress.asStateFlow()

    private val _placeOrder = MutableStateFlow<ResultState<Order>>(ResultState.Loading)
    val placeOrder: StateFlow<ResultState<Order>> = _placeOrder.asStateFlow()

    fun placeOrderNow(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        paymentType: String,
        selectedColor: String
    ) {
        viewModelScope.launch {
            _placeOrder.value = ResultState.Loading
            try {
                val response = repository.placeOrder(
                    userId,
                    productIds,
                    totalQuantity,
                    totalPrice,
                    paymentType,
                    selectedColor
                )
                _placeOrder.value = ResultState.Success(response)
            } catch (e: Exception) {
                _placeOrder.value = ResultState.Error(e)
            }
        }
    }

    fun updateAddress(address: String, city: String, country: String, postalCode: Long) {
        viewModelScope.launch {
            _updateAddress.value = ResultState.Loading
            try {
                val response = repository.updateUsersAddress(address, city, country, postalCode)
                _updateAddress.value = ResultState.Success(response)
            } catch (e: Exception) {
                _updateAddress.value = ResultState.Error(e)
            }
        }
    }

    fun getUserData(id: Int) {
        viewModelScope.launch {
            _userData.value = ResultState.Loading
            try {
                val response = repository.getUserData(id)
                _userData.value = ResultState.Success(response)
            } catch (e: Exception) {
                _userData.value = ResultState.Error(e)
            }
        }
    }

    fun deleteCartItem(id: Long) {
        viewModelScope.launch {
            _deleteItem.value = ResultState.Loading
            try {
                val response = repository.deleteCartItemById(id)
                _deleteItem.value = ResultState.Success(response)
            } catch (e: Exception) {
                _deleteItem.value = ResultState.Error(e)
            }
        }
    }

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