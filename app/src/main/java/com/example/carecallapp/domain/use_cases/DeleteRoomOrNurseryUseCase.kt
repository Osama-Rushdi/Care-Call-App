package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class DeleteRoomOrNurseryUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(id: Int):Boolean {
        return repository.deleteRoomOrNursery(id)
    }
}