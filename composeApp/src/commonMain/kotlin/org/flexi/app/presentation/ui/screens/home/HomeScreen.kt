package org.flexi.app.presentation.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.jan.supabase.gotrue.auth
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.model.user.User
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.BooksList
import org.flexi.app.presentation.ui.components.ElectronicsList
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.FeaturedList
import org.flexi.app.presentation.ui.components.FoodList
import org.flexi.app.presentation.ui.components.FurnituresList
import org.flexi.app.presentation.ui.components.HotSaleItem
import org.flexi.app.presentation.ui.components.LoadingBox
import org.flexi.app.presentation.ui.components.ProductList
import org.flexi.app.presentation.ui.components.PromotionCardWithPager
import org.flexi.app.presentation.ui.components.TopAppBarWithProfile
import org.flexi.app.presentation.ui.screens.auth.login.LoginScreen
import org.flexi.app.presentation.ui.screens.cart.CartList
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.theme.LocalThemeIsDark
import org.flexi.app.utils.Constant.BASE_URL
import org.koin.compose.koinInject

class HomeScreen : Screen {
    @OptIn(
        ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
        ExperimentalFoundationApi::class
    )
    @Composable
    override fun Content() {
        var productsList by remember { mutableStateOf<List<Products>?>(null) }
        var promoList by remember { mutableStateOf<List<PromotionsProductsItem>?>(null) }
        var categoriesList by remember { mutableStateOf<List<Categories>?>(null) }
        var booksList by remember { mutableStateOf<List<BooksItem>?>(null) }
        var cartItemList by remember { mutableStateOf<List<CartItem>?>(null) }
        var userData by remember { mutableStateOf<User?>(null) }
        var isProfile by remember { mutableStateOf(false) }
        val viewModel: MainViewModel = koinInject()
        val scrollState = rememberScrollState()
        val navigator = LocalNavigator.current
        var selectedCategoryIndex by remember { mutableStateOf(0) }
        var query by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            viewModel.getProducts()
            viewModel.getPromotionsItems()
            viewModel.getCategoriesList()
            viewModel.getBooksList()
            viewModel.getCartsList(1)
            viewModel.getUserData(141)
        }
        val state by viewModel.products.collectAsState()
        when (state) {
            is ResultState.Error -> {
                val error = (state as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                LoadingBox()
            }

            is ResultState.Success -> {
                val response = (state as ResultState.Success).response
                productsList = response
            }
        }
        val promoState by viewModel.promotions.collectAsState()
        when (promoState) {
            is ResultState.Error -> {
                val error = (promoState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                // LoadingBox()
            }

            is ResultState.Success -> {
                val response = (promoState as ResultState.Success).response
                promoList = response
            }
        }
        val categoriesState by viewModel.categories.collectAsState()
        when (categoriesState) {
            is ResultState.Error -> {
                val error = (categoriesState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (categoriesState as ResultState.Success).response
                categoriesList = response
            }
        }
        val booksState by viewModel.books.collectAsState()
        when (booksState) {
            is ResultState.Error -> {
                val error = (booksState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                // LoadingBox()
            }

            is ResultState.Success -> {
                val response = (booksState as ResultState.Success).response
                booksList = response
            }
        }
        val cartState by viewModel.carts.collectAsState()
        when (cartState) {
            is ResultState.Error -> {
                val error = (cartState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (cartState as ResultState.Success).response
                cartItemList = response
            }
        }
        val userState by viewModel.userData.collectAsState()
        when (userState) {
            is ResultState.Error -> {
                val error = (userState as ResultState.Error).error
                ErrorBox(error)
            }

            ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val products = (userState as ResultState.Success).response
                userData = products
            }
        }
        var isDark by LocalThemeIsDark.current
        val isSearching = query.isNotBlank()

        val filteredProducts = productsList?.filter {
            it.name.contains(query, ignoreCase = true)
        }

        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }

        fun refresh() {
            refreshScope.launch {
                delay(1500)
                viewModel.getProducts()
                viewModel.getPromotionsItems()
                viewModel.getCategoriesList()
                viewModel.getBooksList()
                viewModel.getCartsList(141)
                refreshing = false
            }
        }

        val username = userData?.fullName.toString()
        val userProfile = BASE_URL + userData?.profileImage.toString()
        val user =
            FlexiApiClient.supaBaseClient.auth.currentSessionOrNull()
        val refreshState = rememberPullRefreshState(refreshing, ::refresh)

        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            topBar = {
                cartItemList?.size?.let {
                    TopAppBarWithProfile(
                        name = username ?: "Pablo Valdes",
                        onCartClicked = {
                            cartItemList?.let { carts ->
                                val mutableCartsList = carts.toMutableList()
                                navigator?.push(CartList(mutableCartsList))
                            }
                        },
                        profileImageUrl = if (user?.user?.email?.isEmpty() == true) null else userProfile,
                        itemCount = it,
                        onProfileClick = {
                            if (user?.user?.email?.isEmpty() == true) {
                                navigator?.push(LoginScreen())
                            } else {
                                isProfile = !isProfile
                            }
                        }
                    )
                }
            }
        ) {
            Box(
                Modifier
                    .pullRefresh(refreshState),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2000.dp)
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding(),
                            start = 6.dp,
                            end = 6.dp
                        )
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            placeholder = {
                                Text(
                                    "Search your Product",
                                    fontSize = 14.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search",
                                    tint = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Card(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FilterAlt,
                                contentDescription = "Filter",
                                modifier = Modifier
                                    .clickable {
                                        isDark = !isDark
                                    }
                                    .padding(12.dp),
                                tint = Color.Gray,
                            )
                        }
                    }
                    if (isSearching) {
                        val lazyState = rememberLazyGridState()
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            state = lazyState,
                            modifier = Modifier.fillMaxWidth()
                                .height(1200.dp).padding(bottom = 34.dp, top = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            filteredProducts?.let { proList ->
                                items(proList) { pro ->
                                    HotSaleItem(pro)
                                }
                            }
                        }
                    } else {
                        promoList?.let { promotion ->
                            PromotionCardWithPager(promotion)
                        }
                        productsList?.let { pro ->
                            FeaturedList(pro)
                            FoodList(pro)
                            FurnituresList(pro)
                            ElectronicsList(pro)
                        }
                        booksList?.let { bookList ->
                            BooksList(bookList)
                        }
                        categoriesList?.let { categories ->
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Categories",
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 28.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                LazyRow {
                                    item {
                                        Text(
                                            text = "All",
                                            fontWeight = if (selectedCategoryIndex == 0) FontWeight.Bold else FontWeight.Normal,
                                            textDecoration = if (selectedCategoryIndex == 0) TextDecoration.Underline else TextDecoration.None,
                                            color = if (selectedCategoryIndex == 0) Color.Black else Color.Gray,
                                            modifier = Modifier.clickable {
                                                selectedCategoryIndex = 0
                                            }
                                                .padding(end = 16.dp)
                                        )
                                    }
                                    itemsIndexed(categories) { index, category ->
                                        Text(
                                            text = category.name,
                                            fontWeight = if (selectedCategoryIndex == index + 1) FontWeight.Bold else FontWeight.Normal,
                                            textDecoration = if (selectedCategoryIndex == index + 1) TextDecoration.Underline else TextDecoration.None,
                                            color = if (selectedCategoryIndex == index + 1) Color.Black else Color.Gray,
                                            modifier = Modifier.clickable {
                                                selectedCategoryIndex = index + 1
                                            }
                                                .padding(end = 16.dp)
                                        )
                                    }
                                }

                                val filteredProducts = if (selectedCategoryIndex == 0) {
                                    productsList
                                } else {
                                    productsList?.filter { it.categoryTitle == categories[selectedCategoryIndex - 1].name }
                                }
                                filteredProducts?.let { productList ->
                                    ProductList(products = productList)
                                }
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = refreshState,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
        }
        if (isProfile) {
            AlertDialog(
                onDismissRequest = { isProfile = false },
                title = {
                    Text(
                        text = userData?.fullName.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                icon = {
                    val profile: Resource<Painter> =
                        asyncPainterResource(BASE_URL + userData?.profileImage.toString())
                    KamelImage(
                        resource = profile,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(24.dp)
                            )
                    )
                },
                text = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "@" + userData?.username.toString(),
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = userData?.country ?: "",
                                fontSize = 14.sp
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Home, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = userData?.address ?: "",
                                fontSize = 14.sp
                            )
                        }
                    }
                },
                confirmButton = {

                    Icon(
                        modifier = Modifier.clickable {
                            isProfile = !isProfile
                        },
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )

                },
                dismissButton = {
                    TextButton(
                        onClick = { isProfile = false },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "")
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                textContentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}