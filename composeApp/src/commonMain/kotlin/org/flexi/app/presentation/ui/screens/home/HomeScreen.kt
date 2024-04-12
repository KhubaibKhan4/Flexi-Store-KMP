package org.flexi.app.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var productsList by remember { mutableStateOf<List<Products>?>(null) }
        val viewModel: MainViewModel = koinInject<MainViewModel>()
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
                productsList?.let { list ->
                    ProductList(products = list)
                }
            }
        }
    }
}