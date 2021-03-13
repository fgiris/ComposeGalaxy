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
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

@ExperimentalStdlibApi
@Composable
fun Galaxy(
    modifier: Modifier = Modifier,
    planetData: PlanetData = PlanetData(),
    planetAnimationSpec: DurationBasedAnimationSpec<Float> = DefaultTweenSpec
) {
    val planetRandomizers = remember {
        generateRandomPlanetDataset(
            planetData = planetData
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    val planetShiftFactor = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = planetAnimationSpec,
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = modifier,
        onDraw = {
            drawGalaxy(
                drawScope = this,
                planetRandomizers = planetRandomizers,
                shiftFactor = planetShiftFactor.value
            )
        }
    )
}

private fun drawGalaxy(
    drawScope: DrawScope,
    planetRandomizers: List<PlanetRandomizer>,
    shiftFactor: Float
) {
    val diagonal = hypot(drawScope.size.width, drawScope.size.height)
    val planetShiftValue = 2 * diagonal * shiftFactor

    // Draw all planets
    for (planetRandomizer in planetRandomizers) {
        drawPlanet(
            radius = planetRandomizer.radius,
            center = getRandomPointOutsideGalaxy(
                drawScope = drawScope,
                centerOffsetXFactor = planetRandomizer.centerOffsetXFactor,
                centerOffsetYFactor = planetRandomizer.centerOffsetYFactor,
                diagonal = diagonal,
                randomAngleInRadians = planetRandomizer.randomAngleInRadians,
                shiftValue = planetShiftValue
            ),
            color = planetRandomizer.color,
            alpha = planetRandomizer.alpha,
            drawScope = drawScope
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

private fun getRandomPointOutsideGalaxy(
    drawScope: DrawScope,
    centerOffsetXFactor: Float,
    centerOffsetYFactor: Float,
    diagonal: Float,
    randomAngleInRadians: Float,
    shiftValue: Float
): Offset {
    // We will first pick a random point inside the screen and
    // move it outside of the screen by adding a factor of diagonal
    val randomXInGalaxy = centerOffsetXFactor * drawScope.size.width
    val randomYInGalaxy = centerOffsetYFactor * drawScope.size.height

    // Replacement values are added to the random x and y.
    // The hypot(replacementX, replacementY) will always be diagonal in order
    // to move the random point by the diagonal.
    // To give a rotation, we will use a coefficient
    // x: sin(randomAngle)
    // y: cos(randomAngle)
    val replacementX = diagonal * sin(randomAngleInRadians)
    val replacementY = diagonal * cos(randomAngleInRadians)

    // We will calculate the shift value for each coordinate by
    // using the same coefficient used for replacement value.
    val shiftValueX = shiftValue * -sin(randomAngleInRadians)
    val shiftValueY = shiftValue * -cos(randomAngleInRadians)

    return Offset(
        x =  randomXInGalaxy + replacementX + shiftValueX,
        y = randomYInGalaxy  + replacementY + shiftValueY
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

@ExperimentalStdlibApi
private fun generateRandomPlanetDataset(
    planetData: PlanetData
): List<PlanetRandomizer> {
    return buildList {
        for (i in 0..planetData.numberOfPlanet) {
            add(
                PlanetRandomizer(
                    centerOffsetXFactor = (0..100).random() / 100f,
                    centerOffsetYFactor = (0..100).random() / 100f,
                    randomAngleInRadians = generateRandomAngle(),
                    centerShiftFactor = getRandomSin(),
                    radius = getRandomPlanetRadius(planetData.maxPlanetRadius),
                    color = getRandomPlanetColor(),
                    alpha = getRandomPlanetAlpha(planetData.maxPlanetAlpha)
                )
            )
        }
    }
}

private fun generateRandomAngle(): Float {
    // Generate an angle in radians between (0, 2 * PI)
    return Math.toRadians((0..360).random().toDouble()).toFloat()
}

private val planetColors = listOf(
    Color.LightGray,
    Color.Gray,
    Color.DarkGray
)

private val DefaultTweenSpec = TweenSpec<Float>(durationMillis = 10000, easing = LinearEasing)

/**
 * Keeps values for randomization
 */
data class PlanetRandomizer(
    val centerOffsetXFactor: Float,
    val centerOffsetYFactor: Float,
    val randomAngleInRadians: Float,
    val centerShiftFactor: Float,
    val radius: Float,
    val color: Color,
    val alpha: Float
)
