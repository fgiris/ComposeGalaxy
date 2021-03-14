package dev.fatihg.galaxycountdown.data

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.ui.graphics.Color

data class PlanetData(
    val numberOfPlanet: Int = DefaultNumberOfPlanet,
    val maxPlanetRadius: Float = DefaultMaxPlanetRadius,
    val maxPlanetAlpha: Float = DefaultMaxPlanetAlpha,
    val planetColors: List<Color> = DefaultPlanetColors,
    val planetAnimationSpec: DurationBasedAnimationSpec<Float> = DefaultTweenSpec
)

private const val DefaultMaxPlanetRadius = 10f
private const val DefaultNumberOfPlanet = 100
private const val DefaultMaxPlanetAlpha = 0.5f

private val DefaultPlanetColors = listOf(
    Color.LightGray,
    Color.Gray,
    Color.DarkGray
)

private val DefaultTweenSpec = TweenSpec<Float>(
    durationMillis = 30000,
    easing = LinearEasing
)
