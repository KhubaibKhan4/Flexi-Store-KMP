package org.flexi.app.presentation.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.flexi.app.presentation.ui.components.CustomTextField
import org.flexi.app.presentation.ui.components.HeadlineText

class SignupScreen : Screen {
    @Composable
    override fun Content() {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
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
                    onValueChange = {
                        username = it
                    },
                    isError = false,
                    leadingIcon = Icons.Outlined.Person,
                    isPasswordVisible = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                CustomTextField(
                    input = email,
                    label = "Email",
                    onValueChange = {
                        email = it
                    },
                    isError = false,
                    leadingIcon = Icons.Outlined.Email,
                    isPasswordVisible = true
                )
                Spacer(modifier = Modifier.height(6.dp))
                CustomTextField(
                    input = password,
                    label = "Password",
                    onValueChange = {
                        password = it
                    },
                    isError = false,
                    leadingIcon = Icons.Outlined.Lock,
                    showPasswordToggle = true,
                    isPasswordVisible = passwordVisible,
                    onPasswordToggleClick = {
                        passwordVisible = !passwordVisible
                    }
                )
            }

        }
    }

}