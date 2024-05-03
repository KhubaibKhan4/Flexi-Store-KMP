package org.flexi.app.presentation.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.flexi.app.data.remote.FlexiApiClient
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

    private val _deleteCart = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val deleteCart: StateFlow<ResultState<String>> = _deleteCart.asStateFlow()

    private val _myOrders = MutableStateFlow<ResultState<List<Order>>>(ResultState.Loading)
    val myOrders: StateFlow<ResultState<List<Order>>> = _myOrders.asStateFlow()

    private val _updateCountry = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val updateCountry: StateFlow<ResultState<Boolean>> = _updateCountry.asStateFlow()

    private val _updateUsersDetails = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val updateUsersDetails: StateFlow<ResultState<Boolean>> = _updateUsersDetails.asStateFlow()

    private val _signupUser = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val signupUser: StateFlow<ResultState<String>> = _signupUser.asStateFlow()

    private val _loginUser = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val loginUser: StateFlow<ResultState<String>> = _loginUser.asStateFlow()

    private val _logOut = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val logOut: StateFlow<ResultState<String>> = _logOut.asStateFlow()

    fun logout(){
        viewModelScope.launch {
            _logOut.value = ResultState.Loading
            try {
                FlexiApiClient.supaBaseClient.auth.signOut()
                _logOut.value = ResultState.Success("Login Successfully...")
            }catch (e: Exception){
                _logOut.value = ResultState.Error(e)
            }
        }
    }
    fun login(
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch {
            _loginUser.value = ResultState.Loading
            try {
                FlexiApiClient.supaBaseClient.auth.signInWith(Email){
                    email = userEmail
                    password = userPassword
                }
                _loginUser.value = ResultState.Success("Login Successfully...")
            }catch (e: Exception){
                _loginUser.value = ResultState.Error(e)
            }
        }
    }
    fun signUp(
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch {
            _signupUser.value = ResultState.Loading
            try {
                FlexiApiClient.supaBaseClient.auth.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                _signupUser.value = ResultState.Success("Registered Successfully...")
            }catch (e: Exception){
                _signupUser.value = ResultState.Error(e)
            }
        }
    }

    fun updateUsersDetails(
        usersId: Int,
        username: String,
        fullName: String,
        email: String,
        address: String,
        city: String,
        country: String,
        postalCode: Long,
        phoneNumber: String,
    ) {
        viewModelScope.launch {
            _updateUsersDetails.value = ResultState.Loading
            try {
                val response = repository.updateUsersDetails(
                    usersId,
                    username,
                    fullName,
                    email,
                    address,
                    city,
                    country,
                    postalCode,
                    phoneNumber
                )
                _updateUsersDetails.value = ResultState.Success(response)
            } catch (e: Exception) {
                _updateUsersDetails.value = ResultState.Error(e)
            }
        }
    }

    fun updateCountry(userId: Int, countryName: String) {
        viewModelScope.launch {
            _updateCountry.value = ResultState.Loading
            try {
                val response = repository.updateCountry(userId, countryName)
                _updateCountry.value = ResultState.Success(response)
            } catch (e: Exception) {
                _updateCountry.value = ResultState.Error(e)
            }
        }
    }

    fun getMyOrders(userId: Int) {
        viewModelScope.launch {
            _myOrders.value = ResultState.Loading
            try {
                val response = repository.getMyOrders(userId)
                _myOrders.value = ResultState.Success(response)
            } catch (e: Exception) {
                _myOrders.value = ResultState.Error(e)
            }
        }
    }

    fun deleteCart(id: Int) {
        viewModelScope.launch {
            _deleteCart.value = ResultState.Loading
            try {
                val response = repository.deleteUserCart(id)
                _deleteCart.value = ResultState.Success(response)
            } catch (e: Exception) {
                _deleteCart.value = ResultState.Error(e)
            }
        }
    }

    fun placeOrderNow(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        selectedColor: String,
        paymentType: String,
    ) {
        viewModelScope.launch {
            _placeOrder.value = ResultState.Loading
            try {
                val response = repository.placeOrder(
                    userId = userId,
                    productIds = productIds,
                    totalQuantity = totalQuantity,
                    totalPrice = totalPrice,
                    selectedColor = selectedColor,
                    paymentType = paymentType
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