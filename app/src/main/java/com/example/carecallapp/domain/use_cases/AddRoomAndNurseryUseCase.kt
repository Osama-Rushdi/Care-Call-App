package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class AddRoomAndNurseryUseCase  @Inject constructor(val repository: MyRepository){
    suspend fun execute(room: RoomAndNursery): RoomAndNursery {
        return repository.addRoomAndNursery(room)
    }


}
