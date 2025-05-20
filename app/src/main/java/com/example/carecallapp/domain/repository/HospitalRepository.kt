package com.example.carecallapp.domain.repository

import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.AmbulanceRegisterRequest
import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.domain.model.auth.HospitalRegisterRequest
import com.example.carecallapp.domain.model.auth.TokenResponse
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital_content.ServiceResponse
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse

interface HospitalRepository {
    suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse>

    //PROFILE
    suspend fun getHospitalDetails(hospitalId: String): HospitalResponse
    suspend fun updateHospitalDetails(
        hospitalId: String,
        hospitalResponse: HospitalResponse
    ): HospitalResponse

    //SERVICES
    suspend fun getAllServices(): List<ServiceResponse>
    suspend fun addService(service: ServiceRequest): ServiceResponse
    suspend fun deleteService(id: Int): Boolean
    suspend fun updateService(id: Int, service: ServiceRequest): Boolean


    //BLOOD BANK
    suspend fun getAllBloodBags(): List<BloodBag>
    suspend fun getBloodById(id: Int): BloodBag
    suspend fun addBlood(blood: BloodBag): BloodBag
    suspend fun updateBlood(id: Int, blood: BloodBag): BloodBag
    suspend fun deleteBlood(id: Int): Boolean

    //NURSERY AND ROOM
    suspend fun getAllRoomsAndNurseries(): List<RoomAndNursery>
    suspend fun getRoomAndNurseryById(id: Int): RoomAndNursery
    suspend fun addRoomAndNursery(room: RoomAndNursery): RoomAndNursery
    suspend fun deleteRoomOrNursery(id: Int): Boolean

    //AUTHENTICATION
    suspend fun doctorRegister(doctorRegisterRequest: DoctorRegisterRequest): Boolean
    suspend fun ambulanceRegister(ambulanceRegisterRequest: AmbulanceRegisterRequest): Boolean
    suspend fun hospitalRegister(hospitalRegisterRequest: HospitalRegisterRequest): Boolean
    suspend fun userLogin(login: LoginRequest): TokenResponse

}