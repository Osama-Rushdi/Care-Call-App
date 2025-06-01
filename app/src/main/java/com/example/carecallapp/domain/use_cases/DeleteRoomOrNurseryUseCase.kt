package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class DeleteRoomOrNurseryUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(id: Int):Boolean {
        return repository.deleteRoomOrNursery(id)
    }
}