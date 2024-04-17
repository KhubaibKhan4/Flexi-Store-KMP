package org.flexi.app.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun generateRandomColor(): Color {
    val random = Random.Default
    val red = random.nextInt(256)
    val green = random.nextInt(256)
    val blue = random.nextInt(256)
    return Color(red, green, blue)
}
