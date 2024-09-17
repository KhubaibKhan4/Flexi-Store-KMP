package org.flexi.app.presentation.ui.screens.profile

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row 
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.avatar
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserSession
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.user.User
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.HotSaleItem
import org.flexi.app.presentation.ui.screens.order.MyProfileOrders
import org.flexi.app.presentation.ui.screens.order.MyProfileWishList
import org.flexi.app.presentation.ui.screens.setting.SettingScreen
import org.flexi.app.presentation.ui.screens.setting.support.SupportScreen
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.theme.LocalThemeIsDark
import org.flexi.app.utils.Constant.BASE_URL
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class ProfileScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val isDark by LocalThemeIsDark.current
        var user: UserSession? = null
        val viewModel: MainViewModel = koinInject()
        val state = rememberLazyGridState()
        val navigator = LocalNavigator.current
        var productList by remember { mutableStateOf<List<Products>?>(null) }
        var userData by remember { mutableStateOf<User?>(null) }
        LaunchedEffect(user) {
            user = FlexiApiClient.supaBaseClient.auth.currentSessionOrNull()
            viewModel.getProducts()
            viewModel.getUserData(141)
        }
        val productState by viewModel.products.collectAsState()
        when (productState) {
            is ResultState.Error -> {
                val error = (productState as ResultState.Error).error
                ErrorBox(error)
            }
 
            ResultState.Loading -> {
                //LoadingBox()
            }

            
            is ResultState.Success -> {
                val products = (productState as ResultState.Success).response
                productList =
                    products.filter { it.discountPrice > 0 }.sortedByDescending { it.discountPrice }
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
        
        val userName = userData?.username.toString() ?: "@am_pablo"
        val fullName = userData?.fullName.toString() ?: "Pablo Valdes"
        val profileImage: Resource<Painter> =
            asyncPainterResource(BASE_URL + userData?.profileImage.toString())
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "My Profile",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                navigator?.push(SettingScreen())
                            },
                            tint = if (isDark) Color.White else Color.Black
                        )
                    },
                )
            },
            modifier =Modifier.fillMaxWidth()
                .offset(y = (-70).dp)
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = if (isDark) MaterialTheme.colorScheme.background else Color.White)
                    .padding(
                        top = padding.calculateTopPadding(),
                        start = 4.dp,
                        end = 4.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier.wrapContentWidth()
                        .padding(top = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImage != null) {
                        KamelImage(
                            resource = profileImage,
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            animationSpec = tween(
                                durationMillis = 1000,
                                delayMillis = 200,
                                easing = FastOutLinearInEasing
                            )
                        )
                    } else {
                        Image(
                            painter = painterResource(Res.drawable.avatar),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                                .clip(CircleShape)
                        )
                    }

                    Box(
                        modifier = Modifier.size(25.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = (-23).dp, y = (-6).dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                    ) {
                        Image(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    fullName.let {
                        Text(
                            text = it,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    userName.let {
                        Text(
                            text = "@$it",
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "My Orders",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                color =if (isDark) Color.White else Color.Black
                            )
                            Text(
                                text = "View All",
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OrderItem(
                                Icons.Outlined.DeliveryDining,
                                text = "Orders",
                                color = Color.Green,
                                onClick = {
                                    navigator?.push(MyProfileOrders(orderType = "Orders"))
                                }
                            )
                            OrderItem(
                                Icons.Outlined.Autorenew,
                                text = "Processing",
                                color = Color.Blue,
                                onClick = {
                                    navigator?.push(MyProfileOrders(orderType = "Processing"))
                                }
                            )
                            OrderItem(
                                Icons.Outlined.LocalShipping,
                                text = "Shipped",
                                color = Color.Magenta,
                                onClick = {
                                    navigator?.push(MyProfileOrders(orderType = "Completed"))
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OrderItem(
                                Icons.Outlined.Cancel,
                                text = "Cancelled",
                                color = Color.Red,
                                onClick = {
                                    navigator?.push(MyProfileOrders(orderType = "Cancelled"))
                                }
                            )
                            OrderItem(
                                Icons.Outlined.Favorite,
                                text = "Wishlist",
                                color = Color.Magenta,
                                onClick = {
                                    navigator?.push(MyProfileWishList())
                                }
                            )
                            OrderItem(
                                Icons.Outlined.SupportAgent,
                                text = "Support",
                                color = Color.Blue,
                                onClick = {
                                    navigator?.push(SupportScreen())
                                }
                            )
                        }

                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hot Sale",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        state = state,
                        modifier = Modifier.fillMaxWidth()
                            .height(900.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        productList?.let { list ->
                            items(list) { pro ->
                                HotSaleItem(pro)
                            }
                        }

                    }
                }

            }
        }
        if (user?.user?.email?.isEmpty() == true) {
            androidx.compose.material.AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(
                        text = "Login Required",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "Please login to continue",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                buttons = {
                    TextButton(
                        onClick = {}
                    ) {
                        Text("Login")
                    }
                }
            )
        }
    }

    @Composable
    fun OrderItem(icon: ImageVector, text: String, color: Color, onClick: () -> Unit) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
                .clickable {
                    onClick()
                }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = color
            )
            Text(
                text = text,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
