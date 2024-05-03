package org.flexi.app.presentation.ui.screens.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Synagogue
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.CustomTextField
import org.flexi.app.presentation.ui.components.HeadlineText
import org.flexi.app.presentation.ui.screens.auth.login.LoginScreen
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.utils.SignupValidation
import org.koin.compose.koinInject
import kotlin.time.Duration.Companion.seconds

class SignupScreen : Screen {
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


        val supabase = createSupabaseClient(
            supabaseUrl = "https://flrflqyxquvzhlvfcbit.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZscmZscXl4cXV2emhsdmZjYml0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQ3MjA3MDgsImV4cCI6MjAzMDI5NjcwOH0.HjJVA5yZdXIKHmICMxucgOJqYSz-APT_pYyEKr9FvaE"
        ) {
            install(Auth)
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineText(text = "Sign up")
            Text(
                text = "Create an account to get started",
                modifier = Modifier.fillMaxWidth()
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
                                // viewModel.signupUser(username, emails, passwords)
                                /*delay(300)
                                navigator?.pop()*/
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Synagogue,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Facebook,
                            contentDescription = null
                        )
                    }
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
                    if (serverBack.contains("Registered Successfully...") || serverBack.contains("Uploaded to Server Successfully ")) {
                        Text(
                            text = "Account created Successfully.",
                            fontSize = 10.sp,
                            color = Color.Green,
                            modifier = Modifier.clickable {
                                navigator?.push(LoginScreen())
                            }
                        )
                        username = ""
                        emails = ""
                        passwords = ""
                        cpassword = ""
                        scope.launch {
                            delay(3.seconds)
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