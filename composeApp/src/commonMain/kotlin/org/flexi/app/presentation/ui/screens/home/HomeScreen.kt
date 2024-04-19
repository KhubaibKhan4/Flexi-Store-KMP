package org.flexi.app.presentation.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.BooksList
import org.flexi.app.presentation.ui.components.ElectronicsList
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.FeaturedList
import org.flexi.app.presentation.ui.components.FoodList
import org.flexi.app.presentation.ui.components.FurnituresList
import org.flexi.app.presentation.ui.components.LoadingBox
import org.flexi.app.presentation.ui.components.ProductList
import org.flexi.app.presentation.ui.components.PromotionCardWithPager
import org.flexi.app.presentation.ui.components.TopAppBarWithProfile
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.koin.compose.koinInject
import org.flexi.app.presentation.ui.screens.home.model.tab.Tab as NewTabs

class HomeScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        var productsList by remember { mutableStateOf<List<Products>?>(null) }
        var promoList by remember { mutableStateOf<List<PromotionsProductsItem>?>(null) }
        var categoriesList by remember { mutableStateOf<List<Categories>?>(null) }
        var booksList by remember { mutableStateOf<List<BooksItem>?>(null) }
        val viewModel: MainViewModel = koinInject<MainViewModel>()
        val scrollState = rememberScrollState()
        var selectedCategoryIndex by remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            viewModel.getProducts()
            viewModel.getPromotionsItems()
            viewModel.getCategoriesList()
            viewModel.getBooksList()
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
                LoadingBox()
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
                LoadingBox()
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
                LoadingBox()
            }

            is ResultState.Success -> {
                val response = (booksState as ResultState.Success).response
                booksList = response
            }
        }
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            topBar = {
                TopAppBarWithProfile(
                    name = "Jonathan",
                    onSearchClicked = {},
                    onNotificationsClicked = {},
                    profileImageUrl = null
                )
            }
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
                                    modifier = Modifier.clickable { selectedCategoryIndex = 0 }
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
    }
}