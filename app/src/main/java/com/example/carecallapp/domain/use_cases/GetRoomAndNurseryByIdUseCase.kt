package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class GetRoomAndNurseryByIdUseCase  @Inject constructor(private val repository: MyRepository)  {

     suspend fun execute(id:Int): RoomAndNursery {
         return repository.getRoomAndNurseryById(id)
     }
 }
