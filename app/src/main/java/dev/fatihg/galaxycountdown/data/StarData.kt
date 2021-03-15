package dev.fatihg.galaxycountdown.data

import androidx.compose.ui.graphics.Color

data class StarData(
    val numberOfStars: Int = DefaultNumberOfStars,
    val starColors: List<Color> = DefaultStarColors
)

private const val DefaultNumberOfStars = 100

private val DefaultStarColors = listOf(
    Color.White,
    Color.Gray,
    Color.DarkGray
)
