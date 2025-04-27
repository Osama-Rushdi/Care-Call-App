package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject

class AddBloodUseCase @Inject constructor( val repository: HospitalRepository){
     suspend fun execute(blood: BloodBag):BloodBag {
        return repository.addBlood(blood)
    }

}