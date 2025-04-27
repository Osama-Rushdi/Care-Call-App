package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class UpdateBloodUseCase  @Inject constructor(private val repository: HospitalRepository)  {
    suspend fun execute(id:Int,blood: BloodBag): BloodBag{
        return repository.updateBlood(id,blood)
    }
}