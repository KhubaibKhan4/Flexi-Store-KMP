package org.flexi.app.presentation.ui.screens.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import io.github.jan.supabase.gotrue.providers.Apple
import io.github.jan.supabase.gotrue.providers.Google
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.CustomTextField
import org.flexi.app.presentation.ui.screens.auth.login.LoginScreen
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.utils.SignupValidation
import org.koin.compose.koinInject
import kotlin.time.Duration.Companion.seconds

class SignupScreen : Screen {
    @OptIn(SupabaseExperimental::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject<MainViewModel>()
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        var username by remember { mutableStateOf("") }
        var emails by remember { mutableStateOf("") }
        var passwords by remember { mutableStateOf("") }
        var cpassword by remember { mutableStateOf("") }
        var serverBack by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var cpasswordVisible by remember { mutableStateOf(false) }

        var usernameError by remember { mutableStateOf<String?>(null) }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var cpasswordError by remember { mutableStateOf<String?>(null) }

        val action = FlexiApiClient.supaBaseClient.composeAuth.rememberSignInWithApple(
            onResult = { result -> viewModel.loginGoogle(result) },
            fallback = {}
        )
        LaunchedEffect(serverBack){
            if (serverBack.contains("Registered Successfully...")){
                viewModel.signupUser(username, emails, passwords)
            }
        }
        val state by viewModel.signup.collectAsState()
        when (state) {
            is ResultState.Error -> {
                val error = (state as ResultState.Error).error
                // ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (state as ResultState.Success).response
                serverBack = response
            }
        }
        val signUpState by viewModel.signupUser.collectAsState()
        when (signUpState) {
            is ResultState.Error -> {
                val error = (signUpState as ResultState.Error).error
                // ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (signUpState as ResultState.Success).response
                serverBack = response
            }
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.clickable {
                                navigator?.pop()
                            })
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Create an Account",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Sign up to get started and access all features",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))
                CustomTextField(
                    input = username,
                    label = "Username",
                    onValueChange = { username = it },
                    isError = usernameError != null,
                    errorText = usernameError,
                    leadingIcon = Icons.Outlined.Person,
                    isPasswordVisible = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                CustomTextField(
                    input = emails,
                    label = "Email",
                    onValueChange = { emails = it },
                    isError = emailError != null,
                    errorText = emailError,
                    leadingIcon = Icons.Outlined.Email,
                    isPasswordVisible = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                CustomTextField(
                    input = passwords,
                    label = "Password",
                    onValueChange = { passwords = it },
                    isError = passwordError != null,
                    errorText = passwordError,
                    leadingIcon = Icons.Outlined.Lock,
                    showPasswordToggle = true,
                    isPasswordVisible = passwordVisible,
                    onPasswordToggleClick = { passwordVisible = !passwordVisible }
                )
                CustomTextField(
                    input = cpassword,
                    label = "Confirm Password",
                    onValueChange = { cpassword = it },
                    isError = cpasswordError != null,
                    errorText = cpasswordError,
                    leadingIcon = Icons.Outlined.Lock,
                    showPasswordToggle = true,
                    isPasswordVisible = cpasswordVisible,
                    onPasswordToggleClick = { cpasswordVisible = !cpasswordVisible }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            usernameError = SignupValidation.validateUsername(username)
                            emailError = SignupValidation.validateEmail(emails)
                            passwordError = SignupValidation.validatePassword(passwords)
                            cpasswordError =
                                SignupValidation.validateConfirmPassword(passwords, cpassword)

                            val errors = listOfNotNull(
                                usernameError?.let { "username" to it },
                                emailError?.let { "email" to it },
                                passwordError?.let { "password" to it },
                                cpasswordError?.let { "cpassword" to it }
                            )

                            if (errors.isEmpty()) {
                                scope.launch {
                                    viewModel.signUp(userEmail = emails, userPassword = passwords)
                                }

                            } else {
                                errors.forEach { (field, errorMessage) ->
                                    when (field) {
                                        "username" -> usernameError = errorMessage
                                        "email" -> emailError = errorMessage
                                        "password" -> passwordError = errorMessage
                                        "cpassword" -> cpasswordError = errorMessage
                                    }
                                }
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
                        Text("Create Account")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "or create with Social Media",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedButton(onClick = { action.startFlow() }) {
                        ProviderButtonContent(Google)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    OutlinedButton(onClick = {}) {
                        ProviderButtonContent(Apple)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "already have account! Login",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.clickable {
                            navigator?.push(LoginScreen())
                        }
                    )
                    if (serverBack.isNotBlank()) {
                        if (serverBack.contains("Registered Successfully...") || serverBack.contains(
                                "Uploaded to Server Successfully "
                            )
                        ) {
                            Text(
                                text = "Account created Successfully.",
                                fontSize = 10.sp,
                                color = Color.Green,
                            )
                            scope.launch {
                                delay(2.seconds)
                                username = ""
                                emails = ""
                                passwords = ""
                                cpassword = ""
                                navigator?.push(LoginScreen())
                            }
                        } else {
                            Text(
                                text = "Error While Creating Account: $serverBack",
                                fontSize = 10.sp,
                                color = Color.Red,
                                modifier = Modifier.clickable {
                                    navigator?.push(LoginScreen())
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}