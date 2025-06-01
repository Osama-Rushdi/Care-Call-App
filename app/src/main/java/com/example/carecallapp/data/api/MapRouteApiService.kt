package com.example.carecallapp.data.api

import com.example.carecallapp.data.model.PersonService.MapRouteResponseDM
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapRouteApiService {
    @GET("route/v1/driving/{startLon},{startLat};{endLon},{endLat}")
    suspend fun getRoute(
        @Path("startLon") startLon: Double,
        @Path("startLat") startLat: Double,
        @Path("endLon") endLon: Double,
        @Path("endLat") endLat: Double,
        @Query("overview") overview: String = "full",
        @Query("geometries") geometries: String = "geojson"
    ): MapRouteResponseDM
}
