package dev.fatihg.galaxycountdown

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.fatihg.galaxycountdown.data.PlanetData

@Composable
fun Galaxy(
    modifier: Modifier = Modifier,
    planetData: PlanetData = PlanetData()
) {
    Canvas(
        modifier = modifier,
        onDraw = {
            drawGalaxy(
                drawScope = this,
                planetData = planetData
            )
        }
    )
}

private fun drawGalaxy(
    drawScope: DrawScope,
    planetData: PlanetData
) {
    // Draw all planets
    for (i in 0..planetData.numberOfPlanet) {
        drawPlanet(
            radius = getRandomPlanetRadius(planetData.maxPlanetRadius),
            center = getRandomPointInGalaxy(drawScope),
            color = getRandomPlanetColor(),
            alpha = getRandomPlanetAlpha(planetData.maxPlanetAlpha),
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

private fun getRandomPointInGalaxy(
    drawScope: DrawScope
): Offset {
    return Offset(
        x = (0..100).random() / 100f * drawScope.size.width,
        y = (0..100).random() / 100f* drawScope.size.height
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

private val planetColors = listOf(
    Color.LightGray,
    Color.Gray,
    Color.DarkGray
)
