package com.example.carecallapp.domain.model


import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.Nursery


data class HospitalServicesTypeResponse(
    val emergencyRoom: List<RoomAndNursery>,
    val bloodBanks: List<BloodBag>,
    val nurseries: List<Nursery>,
    val hospitalName: String
)