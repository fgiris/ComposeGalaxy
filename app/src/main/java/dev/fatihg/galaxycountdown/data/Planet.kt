package dev.fatihg.galaxycountdown.data

data class PlanetData(
    val numberOfPlanet: Int = DefaultNumberOfPlanet,
    val maxPlanetRadius: Float = DefaultMaxPlanetRadius,
    val maxPlanetAlpha: Float = DefaultMaxPlanetAlpha
)

private const val DefaultMaxPlanetRadius = 10f
private const val DefaultNumberOfPlanet = 200
private const val DefaultMaxPlanetAlpha = 0.5f
