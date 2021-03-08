package dev.fatihg.galaxycountdown

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.fatihg.galaxycountdown.data.PlanetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.sin

@ExperimentalStdlibApi
@Composable
fun Galaxy(
    modifier: Modifier = Modifier,
    planetData: PlanetData = PlanetData(),
    planetAnimationSpec: AnimationSpec<Float> = DefaultTweenSpec
) {
    val planetRandomizers = remember {
        generateRandomPlanetDataset(
            planetData = planetData
        )
    }

    val planetAnimationScope = rememberCoroutineScope()
    val planetAnimatable = remember {
        Animatable(0f)
    }

    Canvas(
        modifier = modifier,
        onDraw = {
            drawGalaxy(
                drawScope = this,
                planetData = planetData,
                planetAnimatable = planetAnimatable,
                planetAnimationScope = planetAnimationScope,
                planetAnimationSpec = planetAnimationSpec,
                planetRandomizers = planetRandomizers
            )
        }
    )
}

private fun drawGalaxy(
    drawScope: DrawScope,
    planetData: PlanetData,
    planetAnimatable: Animatable<Float, AnimationVector1D>,
    planetAnimationScope: CoroutineScope,
    planetAnimationSpec: AnimationSpec<Float>,
    planetRandomizers: List<PlanetRandomizer>
) {
    // Draw all planets
    for (planetRandomizer in planetRandomizers) {
        drawPlanet(
            radius = planetRandomizer.radius,
            center = getRandomPointInGalaxy(
                drawScope = drawScope,
                shiftValue = planetAnimatable.value
            ),
            color = planetRandomizer.color,
            alpha = planetRandomizer.alpha,
            drawScope = drawScope
        )

        movePlanet(
            animatable = planetAnimatable,
            animationSpec = planetAnimationSpec,
            animationScope = planetAnimationScope,
            replacementValue = drawScope.size.maxDimension
        )
    }
}

private fun drawPlanet(
    radius: Float,
    center: Offset,
    color: Color,
    alpha: Float,
    drawScope: DrawScope
) {
    drawScope.drawCircle(
        color = color,
        radius = radius,
        center = center,
        alpha = alpha
    )
}

private fun getRandomPointInGalaxy(
    drawScope: DrawScope,
    shiftValue: Float
): Offset {
    val randomSin = getRandomSin()
    val randomCos = 1 / randomSin

    val shiftX = shiftValue * randomSin
    val shiftY = shiftValue * randomCos

    return Offset(
        x = (0..100).random() / 100f * drawScope.size.width + shiftX,
        y = (0..100).random() / 100f * drawScope.size.height + shiftY
    )
}

private fun getRandomPlanetRadius(
    maxRadius: Float
): Float {
    return (0..100).random() / 100f * maxRadius
}

private fun getRandomPlanetAlpha(
    maxAlpha: Float
): Float {
    return (0..100).random() / 100f * maxAlpha
}

private fun getRandomPlanetColor() = planetColors.random()

private fun getRandomSin(): Float {
    val randomAngle = (0..360).random().toDouble()
    return sin(Math.toRadians(randomAngle)).toFloat()
}

private fun movePlanet(
    animatable: Animatable<Float, AnimationVector1D>,
    animationSpec: AnimationSpec<Float>,
    animationScope: CoroutineScope,
    replacementValue: Float
) {
    animationScope.launch {
        animatable.animateTo(
            targetValue = replacementValue,
            animationSpec = animationSpec
        )
    }
}

@ExperimentalStdlibApi
private fun generateRandomPlanetDataset(
    planetData: PlanetData
): List<PlanetRandomizer> {
    return buildList {
        for (i in 0..planetData.numberOfPlanet) {
            add(
                PlanetRandomizer(
                    centerOffsetFactor = (0..100).random() / 100f,
                    centerShiftFactor = getRandomSin(),
                    radius = getRandomPlanetRadius(planetData.maxPlanetRadius),
                    color = getRandomPlanetColor(),
                    alpha = getRandomPlanetAlpha(planetData.maxPlanetAlpha)
                )
            )
        }
    }
}

private val planetColors = listOf(
    Color.LightGray,
    Color.Gray,
    Color.DarkGray
)

private val DefaultTweenSpec = TweenSpec<Float>(durationMillis = 60000, easing = LinearEasing)

/**
 * Keeps values for randomization
 */
data class PlanetRandomizer(
    val centerOffsetFactor: Float,
    val centerShiftFactor: Float,
    val radius: Float,
    val color: Color,
    val alpha: Float
)
