package org.flexi.app.presentation.ui.screens.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MapsHomeWork
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.launch
import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.domain.model.user.User
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.NotificationsAlertDialog
import org.flexi.app.presentation.ui.screens.auth.login.LoginScreen
import org.flexi.app.presentation.ui.screens.setting.account.AccountScreen
import org.flexi.app.presentation.ui.screens.setting.address.AddressScreen
import org.flexi.app.presentation.ui.screens.setting.country.CountryScreen
import org.flexi.app.presentation.ui.screens.setting.privacy.PrivacyScreen
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.theme.LocalThemeIsDark
import org.koin.compose.koinInject

class SettingScreen(
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var user: UserInfo? = null
        val userLogout = FlexiApiClient.supaBaseClient.auth
        val navigator = LocalNavigator.current
        val viewMode: MainViewModel = koinInject()
        val notificationsDialogVisible = remember { mutableStateOf(false) }
        var userData by remember { mutableStateOf<User?>(null) }
        val scope = rememberCoroutineScope()
        LaunchedEffect(user) {
            user = FlexiApiClient.supaBaseClient.auth.currentUserOrNull()
            viewMode.getUserData(141)
        }
        val userState by viewMode.userData.collectAsState()
        when (userState) {
            is ResultState.Error -> {
                val error = (userState as ResultState.Error).error
                ErrorBox(error)
            }

            ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val data = (userState as ResultState.Success).response
                userData = data
            }
        }
        val userCountry = userData?.country ?: "United States"
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator?.pop()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Options",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                )
            },
            modifier = Modifier.fillMaxWidth()
                .offset(y = (-70).dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = it.calculateTopPadding())
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "General",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SettingItem(
                        "Account Setting",
                        "",
                        icon = Icons.Outlined.PersonOutline,
                        onClick = {
                            userData?.let { user ->
                                navigator?.push(AccountScreen(user))
                            }
                        })
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingItem(
                        "Address Book",
                        "",
                        Icons.Outlined.MapsHomeWork,
                        onClick = { navigator?.push(AddressScreen()) })
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingItem(
                        "Country",
                        userCountry,
                        Icons.Outlined.LocationOn,
                        onClick = { navigator?.push(CountryScreen(userCountry)) })
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingItem("Currency", "$", Icons.Outlined.CurrencyBitcoin, onClick = {})
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingItem("Language", "English", Icons.Outlined.Language, onClick = {})
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Preferences",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    SettingItem(
                        "Notifications Settings",
                        "",
                        Icons.Outlined.Notifications,
                        onClick = {
                            notificationsDialogVisible.value = true
                        })
                    if (notificationsDialogVisible.value) {
                        NotificationsAlertDialog(
                            onDismiss = {
                                notificationsDialogVisible.value = false
                            },
                            onConfirm = { /* Handle turning on notifications */ }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingItem("Privacy & Policy", "", Icons.Outlined.PrivacyTip, onClick = {
                        navigator?.push(
                            PrivacyScreen()
                        )
                    })
                }
                Spacer(modifier = Modifier.height(24.dp))
                val loginText = if (user?.email?.isEmpty() == true) {
                    "Login"
                } else {
                    "Logout"
                }
                Button(
                    onClick = {
                        scope.launch {
                            if (loginText == "Logout") {
                                viewMode.logout()
                                userLogout.clearSession()
                                userLogout.signOut()
                            } else {
                                navigator?.push(LoginScreen())
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5821c4),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        loginText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    @Composable
    fun SettingItem(
        text: String,
        forwardText: String,
        icon: ImageVector,
        onClick: () -> Unit,
    ) {
        val isDark by LocalThemeIsDark.current
        Card(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onClick()
                },
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor =if (isDark) Color.Black else Color.Black
            ),

            ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = text,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = forwardText,
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}