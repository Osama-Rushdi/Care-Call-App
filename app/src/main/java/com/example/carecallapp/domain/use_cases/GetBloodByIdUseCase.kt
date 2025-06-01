package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.BloodBag
import com.example.carecallapp.domain.repository.MyRepository
import javax.inject.Inject

class GetBloodByIdUseCase  @Inject constructor(private val repository: MyRepository)  {
    suspend fun execute(id:Int): BloodBag {
        return repository.getBloodById(id)
    }
}