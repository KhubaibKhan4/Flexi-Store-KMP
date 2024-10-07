package org.flexi.app.presentation.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                color = Color(0xFF5821c4)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Flexi",
                fontSize = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = "Any shopping just from home",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        Text(
            text = "Version 0.0.1",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                .align(Alignment.BottomCenter)
        )
    }
}