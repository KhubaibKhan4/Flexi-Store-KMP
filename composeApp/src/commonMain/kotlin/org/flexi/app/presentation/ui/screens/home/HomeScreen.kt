package org.flexi.app.presentation.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.CategoriesList
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.FeaturedList
import org.flexi.app.presentation.ui.components.FoodList
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
        val viewModel: MainViewModel = koinInject<MainViewModel>()
        val scrollState = rememberScrollState()
        val selectedTabIndex = remember { mutableStateOf(NewTabs.Home) }
        LaunchedEffect(Unit) {
            viewModel.getProducts()
            viewModel.getPromotionsItems()
            viewModel.getCategoriesList()
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
                }
                TabRow(
                    selectedTabIndex = selectedTabIndex.value.ordinal,
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 12.dp),
                    indicator = { tabPositions ->
                        if (selectedTabIndex.value.ordinal < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(
                                    tabPositions[selectedTabIndex.value.ordinal]
                                ),
                                height = 2.dp,
                                color = Color.Red
                            )
                        }
                    }
                ) {
                    Tab(
                        unselectedContentColor = Color.DarkGray,
                        selected = selectedTabIndex.value == NewTabs.Home,
                        onClick = { selectedTabIndex.value = NewTabs.Home }) {
                        Text(text = "Home")
                    }
                    Tab(
                        unselectedContentColor = Color.DarkGray,
                        selected = selectedTabIndex.value == NewTabs.Categories,
                        onClick = {
                            selectedTabIndex.value = NewTabs.Categories
                        }) {
                        Text(text = "Categories")
                    }
                }
                when (selectedTabIndex.value) {
                    NewTabs.Home -> {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .height(765.dp)
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            productsList?.let { list ->
                                ProductList(products = list)
                            }
                        }
                    }

                    NewTabs.Categories -> {
                        categoriesList?.let { category ->
                            CategoriesList(category)
                        }
                    }
                }

            }
        }
    }
}