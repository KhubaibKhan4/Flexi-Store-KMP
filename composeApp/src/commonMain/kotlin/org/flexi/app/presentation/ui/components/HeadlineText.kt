package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HeadlineText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier.wrapContentWidth(),
        color = Color.Black,
        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
    )
}