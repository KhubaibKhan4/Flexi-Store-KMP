package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    input: String,
    label: String,
    leadingIcon: ImageVector? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    isPasswordVisible: Boolean = false,
    showPasswordToggle: Boolean = false,
    onPasswordToggleClick: () -> Unit = {},
) {
    Column {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold
        )
        TextField(
            value = input,
            onValueChange = onValueChange,
            placeholder = {
                Text(label)
            },
            leadingIcon = {
                leadingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Color.Blue
                    )
                }
            },
            modifier = modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isError) Color.Red else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ),
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (showPasswordToggle) {
                    IconButton(
                        onClick = onPasswordToggleClick,
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
                            tint = Color.Gray
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                errorCursorColor = Color.Red,
                errorIndicatorColor = Color.Transparent,
                errorLabelColor = Color.Red,
                errorTrailingIconColor = Color.Red
            )
        )
        if (isError && errorText != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = errorText,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
