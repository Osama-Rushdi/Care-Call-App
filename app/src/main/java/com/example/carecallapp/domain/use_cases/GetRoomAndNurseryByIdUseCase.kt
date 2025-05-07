package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class GetRoomAndNurseryByIdUseCase  @Inject constructor(private val repository: HospitalRepository)  {

     suspend fun execute(id:Int): RoomAndNursery {
         return repository.getRoomAndNurseryById(id)
     }


 }
