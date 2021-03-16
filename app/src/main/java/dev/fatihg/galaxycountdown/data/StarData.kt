package dev.fatihg.galaxycountdown.data

import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Color

data class StarData(
    val numberOfStars: Int = DefaultNumberOfStars,
    val starColors: List<Color> = DefaultStarColors,
    val starShiningAnimationSpec: DurationBasedAnimationSpec<Float> = DefaultStarShiningAnimationSpec
)

private const val DefaultNumberOfStars = 100
private val DefaultStarShiningAnimationSpec = TweenSpec<Float>(
    durationMillis = 3000,
    easing = FastOutSlowInEasing
)

private val DefaultStarColors = listOf(
    Color.White,
    Color.Gray,
    Color.DarkGray
)
