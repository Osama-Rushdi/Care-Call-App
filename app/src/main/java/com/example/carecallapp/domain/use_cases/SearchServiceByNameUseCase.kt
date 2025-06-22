package com.example.carecallapp.domain.use_cases

import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceResponse
import javax.inject.Inject


class SearchServiceByNameUseCase @Inject constructor() {
     fun execute(name: String, list: List<ServiceResponse>): Boolean {
         list.forEach {
             return it.name == name
         }
        return false
    }}