package com.example.carecallapp.data.model.PersonService

data class MapRouteResponseDM(val routes: List<Route>?)

    data class Route(
        val geometry: Geometry
    )

    data class Geometry(
        val coordinates: List<List<Double>>
    )

