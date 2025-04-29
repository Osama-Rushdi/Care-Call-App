package com.example.carecallapp.domain.use_cases

import android.util.Log
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class DeleteRoomOrNurseryUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(id: Int):Boolean {
        Log.d("kkk", "deleteRoomOrNursery:${repository.deleteRoomOrNursery(id)} ")
        return repository.deleteRoomOrNursery(id)
    }
}