package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class DoctorRegisterUseCase @Inject constructor(private val repository: MyRepository) {
    suspend fun execute(doctorRegisterRequest: DoctorRegisterRequest): Boolean {
        return repository.doctorRegister(doctorRegisterRequest)
    }
}