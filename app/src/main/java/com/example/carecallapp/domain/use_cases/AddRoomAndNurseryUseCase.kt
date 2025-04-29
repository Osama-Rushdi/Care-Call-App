package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class AddRoomAndNurseryUseCase  @Inject constructor(val repository: HospitalRepository){
    suspend fun execute(room: RoomAndNursery): RoomAndNursery {
        return repository.addRoomAndNursery(room)
    }


}
