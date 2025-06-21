package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.person_service.MapRouteDomain
import com.example.carecallapp.domain.repository.MyRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetRouteUseCase  @Inject
constructor(private val repository: MyRepository)  {
    suspend fun execute(start: LatLng, end: LatLng):Result<MapRouteDomain> {
            return repository.getRoute(start, end)

    }
}