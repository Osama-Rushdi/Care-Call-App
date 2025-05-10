package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.auth.DoctorRegisterResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class DoctorRegisterUseCase @Inject constructor(private val repository: HospitalRepository) {
    suspend fun execute(doctorRegisterResponse: DoctorRegisterResponse): Boolean {
        return repository.doctorRegister(doctorRegisterResponse)
    }
}