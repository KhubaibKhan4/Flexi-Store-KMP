package org.flexi.app.presentation.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.LoadingBox
import org.flexi.app.presentation.ui.screens.auth.signup.SignupScreen
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Signup",
                modifier = Modifier.clickable {
                    navigator?.push(SignupScreen())
                }
            )
            LazyColumn {
                productsList?.let {products ->
                    items(products){
                        Text(it.toString())
                    }
                }
            }
        }
    }
}