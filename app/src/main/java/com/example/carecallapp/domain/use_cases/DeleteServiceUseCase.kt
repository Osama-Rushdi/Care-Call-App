package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class DeleteServiceUseCase @Inject constructor(val repository: HospitalRepository) {
    suspend fun execute(id: Int): Boolean {
        return repository.deleteService(id)
    }
}