package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    input: String,
    label: String,
    leadingIcon: ImageVector? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
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
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = if (isError) Color.Red else Color.LightGray,
                ),
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(12.dp)
        )
    }
}
