package org.flexi.app.presentation.ui.screens.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithApple
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.createDefaultSessionManager
import io.github.jan.supabase.gotrue.providers.Apple
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.CustomTextField
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.navigation.tabs.main.MainScreen
import org.flexi.app.presentation.ui.screens.auth.signup.SignupScreen
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.utils.SignupValidation
import org.koin.compose.koinInject
import kotlin.time.Duration.Companion.milliseconds

class LoginScreen : Screen {
    @OptIn(SupabaseExperimental::class)
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject<MainViewModel>()
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var loginResponse by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        val state by viewModel.login.collectAsState()

        val action = FlexiApiClient.supaBaseClient.composeAuth.rememberSignInWithApple(
            onResult = { result -> viewModel.loginGoogle(result) },
            fallback = {}
        )
        when (state) {
            is ResultState.Error -> {
                val error = (state as ResultState.Error).error
                ErrorBox(error)
                isLoading = false
            }

            is ResultState.Loading -> {

            }

            is ResultState.Success -> {
                val response = (state as ResultState.Success).response
                loginResponse = response
                isLoading = false
            }
        }

        val loginState by viewModel.loginUser.collectAsState()

        when (loginState) {
            is ResultState.Error -> {
                val error = (loginState as ResultState.Error).error
                ErrorBox(error)
                isLoading = false
            }

            is ResultState.Loading -> {

            }

            is ResultState.Success -> {
                val response = (loginState as ResultState.Success).response
                loginResponse = response
                isLoading = false
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Login to your account to continue",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            CustomTextField(
                input = email,
                label = "Email",
                onValueChange = {
                    email = it
                    emailError = SignupValidation.validateEmail(it)
                },
                isError = emailError != null,
                leadingIcon = Icons.Outlined.Email,
                isPasswordVisible = true
            )
            Spacer(modifier = Modifier.height(6.dp))
            CustomTextField(
                input = password,
                label = "Password",
                onValueChange = {
                    password = it
                    passwordError = SignupValidation.validatePassword(it)
                },
                isError = passwordError != null,
                leadingIcon = Icons.Outlined.Lock,
                showPasswordToggle = true,
                isPasswordVisible = passwordVisible,
                onPasswordToggleClick = { passwordVisible = !passwordVisible }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        if (emailError == null && passwordError == null) {
                            isLoading = true
                            viewModel.login(userEmail = email, userPassword = password)
                            viewModel.loginUser(email, password)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                ) {
                    Text("Sign In")
                    Spacer(modifier = Modifier.width(4.dp))
                    AnimatedVisibility(isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(25.dp),
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "or using other method",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(onClick = { action.startFlow() }) {
                    ProviderButtonContent(Google)
                }
                Spacer(modifier = Modifier.height(2.dp))
                OutlinedButton(onClick = { action.startFlow() }) {
                    ProviderButtonContent(Apple)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "don't have account! Signup",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        navigator?.push(SignupScreen())
                    }
                )
                if (loginResponse.contains("Login Successful")) {
                    Text(
                        text = "Login Successful",
                        fontSize = 10.sp,
                        color = Color.Red,
                    )
                    scope.launch {
                        delay(500.milliseconds)
                        if (loginResponse.contains("Invalid Email or Password")) {
                            isLoading = false
                            return@launch
                        } else {
                            email = ""
                            password = ""
                            loginResponse = ""
                            isLoading = false
                           /* val user =
                                FlexiApiClient.supaBaseClient.auth.currentSessionOrNull()
                            val userEmail = user?.user?.email*/
                              navigator?.push(MainScreen(userEmail = null))
                        }
                    }
                } else {
                    Text(
                        text = loginResponse,
                        fontSize = 10.sp,
                        color = Color.Red,
                    )
                    isLoading = false
                }
            }
        }
    }
}