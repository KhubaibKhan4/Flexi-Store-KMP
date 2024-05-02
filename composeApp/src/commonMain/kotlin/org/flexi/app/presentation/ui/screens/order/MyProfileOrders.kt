package org.flexi.app.presentation.ui.screens.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.no_item_found
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class MyProfileOrders(
  private val orderType: String
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject()
        val navigator = LocalNavigator.current
        var myOrderData by remember { mutableStateOf<List<Order>?>(null) }
        var productIdsList by remember { mutableStateOf<List<Long>?>(null) }
        var productList by remember { mutableStateOf<List<Products>?>(null) }
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        LaunchedEffect(Unit) {
            viewModel.getMyOrders(1)
        }
        LaunchedEffect(productIdsList) {
            productIdsList?.let { viewModel.getProductById(it) }
        }
        val myOrderState by viewModel.myOrders.collectAsState()
        when (myOrderState) {
            is ResultState.Error -> {
                val error = (myOrderState as ResultState.Error).error
                // ErrorBox(error)
            }

            ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (myOrderState as ResultState.Success).response
                myOrderData = response
                productIdsList = response.map { it.productIds.toLong() }.distinct()
            }
        }
        val productState by viewModel.productItem.collectAsState()
        when (productState) {
            is ResultState.Error -> {
                val error = (productState as ResultState.Error).error
                // ErrorBox(error)
            }

            ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (productState as ResultState.Success).response
                productList = response
            }
        }


        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text =if (orderType=="Orders") "My All Orders" else if (orderType=="Processing") "My Processing Orders" else "My Completed Orders",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                navigator?.pop()
                            }
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = 34.dp,
                        start = 6.dp,
                        end = 6.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (productList?.isEmpty() == true) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = it.calculateTopPadding(), bottom = 34.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.no_item_found),
                                    contentDescription = null,
                                    modifier = Modifier.size(250.dp)
                                )
                                Text(
                                    text = "No Active Order available",
                                    modifier = Modifier.padding(12.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                            }
                        }
                    } else {
                       if (orderType=="Orders"){
                           val activeOrders =
                               myOrderData?.filter { it.orderProgress == "On Progress" || it.orderProgress == "On The Way" || it.orderProgress == "Completed" }
                           LazyVerticalGrid(
                               columns = GridCells.Adaptive(300.dp),
                               modifier = Modifier.fillMaxWidth(),
                               horizontalArrangement = Arrangement.Center,
                               verticalArrangement = Arrangement.Center,
                               contentPadding = PaddingValues(6.dp)
                           ) {
                               activeOrders?.let { orders ->
                                   items(orders) { order ->
                                       val product =
                                           productList?.find { it.id == order.productIds }
                                       product?.let { MyOrderItems(it, order) }
                                   }
                               }
                           }
                       }else if(orderType=="Processing"){
                           val activeOrders =
                               myOrderData?.filter { it.orderProgress == "On Progress" || it.orderProgress == "On The Way"}
                           if (activeOrders?.isEmpty()==true){
                               Column(
                                   modifier = Modifier.fillMaxWidth()
                                       .padding(top = it.calculateTopPadding(), bottom = 34.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = Arrangement.Center
                               ) {
                                   Column(
                                       modifier = Modifier.fillMaxSize(),
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.Center
                                   ) {
                                       Image(
                                           painter = painterResource(Res.drawable.no_item_found),
                                           contentDescription = null,
                                           modifier = Modifier.size(250.dp)
                                       )
                                       Text(
                                           text = "No Active Order available",
                                           modifier = Modifier.padding(12.dp),
                                           fontWeight = FontWeight.Bold,
                                           color = Color.Red
                                       )
                                   }
                               }
                           }
                           LazyVerticalGrid(
                               columns = GridCells.Adaptive(300.dp),
                               modifier = Modifier.fillMaxWidth(),
                               horizontalArrangement = Arrangement.Center,
                               verticalArrangement = Arrangement.Center,
                               contentPadding = PaddingValues(6.dp)
                           ) {
                               activeOrders?.let { orders ->
                                   items(orders) { order ->
                                       val product =
                                           productList?.find { it.id == order.productIds }
                                       product?.let { MyOrderItems(it, order) }
                                   }
                               }
                           }
                       }else if (orderType == "Completed"){
                           val activeOrders =
                               myOrderData?.filter { it.orderProgress == "Completed"}
                           if (activeOrders?.isEmpty()==true){
                               Column(
                                   modifier = Modifier.fillMaxWidth()
                                       .padding(top = it.calculateTopPadding(), bottom = 34.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = Arrangement.Center
                               ) {
                                   Column(
                                       modifier = Modifier.fillMaxSize(),
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.Center
                                   ) {
                                       Image(
                                           painter = painterResource(Res.drawable.no_item_found),
                                           contentDescription = null,
                                           modifier = Modifier.size(250.dp)
                                       )
                                       Text(
                                           text = "No Active Order available",
                                           modifier = Modifier.padding(12.dp),
                                           fontWeight = FontWeight.Bold,
                                           color = Color.Red
                                       )
                                   }
                               }
                           }
                           LazyVerticalGrid(
                               columns = GridCells.Adaptive(300.dp),
                               modifier = Modifier.fillMaxWidth(),
                               horizontalArrangement = Arrangement.Center,
                               verticalArrangement = Arrangement.Center,
                               contentPadding = PaddingValues(6.dp)
                           ) {
                               activeOrders?.let { orders ->
                                   items(orders) { order ->
                                       val product =
                                           productList?.find { it.id == order.productIds }
                                       product?.let { MyOrderItems(it, order) }
                                   }
                               }
                           }
                       }else if (orderType=="Cancelled"){
                           val activeOrders =
                               myOrderData?.filter { it.orderProgress == "Cancelled"}
                           if (activeOrders?.isEmpty()==true){
                               Column(
                                   modifier = Modifier.fillMaxWidth()
                                       .padding(top = it.calculateTopPadding(), bottom = 34.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = Arrangement.Center
                               ) {
                                   Column(
                                       modifier = Modifier.fillMaxSize(),
                                       horizontalAlignment = Alignment.CenterHorizontally,
                                       verticalArrangement = Arrangement.Center
                                   ) {
                                       Image(
                                           painter = painterResource(Res.drawable.no_item_found),
                                           contentDescription = null,
                                           modifier = Modifier.size(250.dp)
                                       )
                                       Text(
                                           text = "No Active Order available",
                                           modifier = Modifier.padding(12.dp),
                                           fontWeight = FontWeight.Bold,
                                           color = Color.Red
                                       )
                                   }
                               }
                           }
                           LazyVerticalGrid(
                               columns = GridCells.Adaptive(300.dp),
                               modifier = Modifier.fillMaxWidth(),
                               horizontalArrangement = Arrangement.Center,
                               verticalArrangement = Arrangement.Center,
                               contentPadding = PaddingValues(6.dp)
                           ) {
                               activeOrders?.let { orders ->
                                   items(orders) { order ->
                                       val product =
                                           productList?.find { it.id == order.productIds }
                                       product?.let { MyOrderItems(it, order) }
                                   }
                               }
                           }
                       }else{
                           EmptyOrder()
                       }
                    }


                }

            }
        }
    }
}

@Composable
fun EmptyOrder() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.no_item_found),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Text(
                text = "No Active Order available",
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}