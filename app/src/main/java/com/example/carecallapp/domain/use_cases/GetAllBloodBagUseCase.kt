package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class GetAllBloodBagUseCase  @Inject constructor(private val repository: HospitalRepository)  {

    suspend fun execute(): List<BloodBag> {
        return repository.getAll()
    }


}