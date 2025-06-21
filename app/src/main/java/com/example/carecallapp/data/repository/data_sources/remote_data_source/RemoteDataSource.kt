package com.example.carecallapp.data.repository.data_sources.remote_data_source

import com.example.carecallapp.domain.model.person_service.LocationRequest
import com.example.carecallapp.domain.model.person_service.MapRouteDomain
import com.example.carecallapp.domain.model.person_service.PersonNotificationResponse
import com.example.carecallapp.domain.model.person_service.ambulance.AmbulanceProfile
import com.example.carecallapp.domain.model.person_service.doctor.DoctorProfile
import com.example.carecallapp.domain.model.auth.AmbulanceRegisterRequest
import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.domain.model.auth.HospitalRegisterRequest
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.TokenResponse
import com.example.carecallapp.domain.model.hospital.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceResponse
import com.example.carecallapp.domain.model.hospital.hospital_notification.HospitalNotificationResponse
import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import com.google.android.gms.maps.model.LatLng


interface RemoteDataSource {

    //get ambulance and doctor accounts
    suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse>

    //profile get-edit
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

    //Blood bank
    suspend fun getBloodAll(): List<BloodBag>
    suspend fun getBloodById(id: Int): BloodBag
    suspend fun addBlood(blood: BloodBag): BloodBag
    suspend fun updateBlood(id: Int, blood: BloodBag): BloodBag
    suspend fun deleteBlood(id: Int): Boolean

    //ROOM AND NURSERY
    suspend fun getAllRoomsAndNurseries(): List<RoomAndNursery>
    suspend fun getRoomAndNurseryById(id: Int): RoomAndNursery
    suspend fun addRoomAndNursery(room: RoomAndNursery): RoomAndNursery
    suspend fun deleteRoomOrNursery(id: Int): Boolean

    //AUTHENTICATION
    suspend fun doctorRegister(doctorRegisterRequest: DoctorRegisterRequest): Boolean
    suspend fun ambulanceRegister(ambulanceRegisterRequest: AmbulanceRegisterRequest): Boolean
    suspend fun hospitalRegister(hospitalRegisterRequest: HospitalRegisterRequest): Boolean
    suspend fun userLogin(login: LoginRequest): TokenResponse

    //All REQUESTS HOSPITAL,AMBULANCE AND DOCTOR

    suspend fun getCurrentPersonRequest(): PersonNotificationResponse
    suspend fun getPersonRequests(): List<PersonNotificationResponse>
    suspend fun confirmPersonRequest(id: Int): Boolean
    suspend fun completePersonRequest(id: Int): Boolean
    suspend fun cancelPersonRequest(id: Int): Boolean
    suspend fun getHospitalRequests(): List<HospitalNotificationResponse>

    //DOCTOR
    suspend fun getDoctorDetails(doctorId: String): DoctorProfile?
    suspend fun updateDoctorDetails(doctorId: String, doctorProfile: DoctorProfile): Boolean
    suspend fun updateDoctorLocation(locationRequest: LocationRequest): Boolean

    // Ambulance
    suspend fun getAmbulanceDetails(ambulanceId: String): AmbulanceProfile?
    suspend fun updateAmbulanceDetails(
        ambulanceId: String,
        ambulanceProfile: AmbulanceProfile
    ): Boolean

    suspend fun updateAmbulanceLocation(locationRequest: LocationRequest): Boolean

    //MAP
    suspend fun getRoute(start: LatLng, end: LatLng): Result<MapRouteDomain>
}
