package org.flexi.app.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.LoadingBox
import org.flexi.app.presentation.ui.components.ProductList
import org.flexi.app.presentation.ui.components.TopAppBarWithProfile
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.koin.compose.koinInject
import org.flexi.app.presentation.ui.screens.home.model.tab.Tab as NewTabs

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var productsList by remember { mutableStateOf<List<Products>?>(null) }
        val viewModel: MainViewModel = koinInject<MainViewModel>()
        val selectedTabIndex = remember { mutableStateOf(NewTabs.Home) }
        LaunchedEffect(Unit) {
            viewModel.getProducts()
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
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                        start = 6.dp,
                        end = 6.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
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
                        productsList?.let { list ->
                            ProductList(products = list)
                        }
                    }

                    NewTabs.Categories -> {
                        Text("Nothing Here ")
                    }
                }

            }
        }
    }
}