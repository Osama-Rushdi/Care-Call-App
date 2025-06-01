package com.example.carecallapp.data.repository.repository_imp

import com.example.carecallapp.data.repository.Connectivity
import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.PersonService.LocationRequest
import com.example.carecallapp.domain.model.PersonService.MapRouteDomain
import com.example.carecallapp.domain.model.PersonService.PersonNotificationResponse
import com.example.carecallapp.domain.model.PersonService.ambulance.AmbulanceProfile
import com.example.carecallapp.domain.model.PersonService.doctor.DoctorProfile
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
import com.example.carecallapp.domain.model.hospital.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.MyRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


class MyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val connectivity: Connectivity,
) : MyRepository {

    //get accounts doctor and ambulance
    override suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getPeopleAccounts(accountType, hospitalId)
        } else
            throw Exception("no Internet")
    }

    //hospital profile  get- edit
    override suspend fun getHospitalDetails(hospitalId: String): HospitalResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.getHospitalDetails(hospitalId)
        } else
            throw Exception("no Internet")
    }

    override suspend fun updateHospitalDetails(
        hospitalId: String,
        hospitalResponse: HospitalResponse
    ): HospitalResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateHospitalDetails(hospitalId, hospitalResponse)
        } else
            throw Exception("no Internet")
    }

    override suspend fun getAllServices(): List<ServiceResponse> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getAllServices()
        } else
            throw Exception("no Internet")

    }

    override suspend fun addService(service: ServiceRequest): ServiceResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.addService(service)
        } else
            throw Exception("no Internet")
    }

    override suspend fun deleteService(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.deleteService(id)
        } else
            throw Exception("no Internet")
    }

    override suspend fun updateService(id: Int, service: ServiceRequest): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateService(id, service)
        } else
            throw Exception("no Internet")
    }

    //blood bank
    override suspend fun getAllBloodBags(): List<BloodBag> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getBloodAll()
        } else
            throw Exception("no Internet")
    }

    override suspend fun getBloodById(id: Int): BloodBag {
        return if (connectivity.isOnline()) {
            remoteDataSource.getBloodById(id)
        } else
            throw Exception("no Internet")
    }

    override suspend fun addBlood(blood: BloodBag): BloodBag {
        return if (connectivity.isOnline()) {
            remoteDataSource.addBlood(blood)
        } else
            throw Exception("no Internet")
    }

    override suspend fun updateBlood(id: Int, blood: BloodBag): BloodBag {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateBlood(id, blood)
        } else
            throw Exception("no Internet")
    }

    override suspend fun deleteBlood(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.deleteBlood(id)
        } else
            throw Exception("no Internet")
    }

    //room and nursery
    override suspend fun getAllRoomsAndNurseries(): List<RoomAndNursery> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getAllRoomsAndNurseries()
        } else
            throw Exception("no Internet")
    }

    override suspend fun getRoomAndNurseryById(id: Int): RoomAndNursery {
        return if (connectivity.isOnline()) {
            remoteDataSource.getRoomAndNurseryById(id)
        } else
            throw Exception("no Internet")
    }

    override suspend fun addRoomAndNursery(room: RoomAndNursery): RoomAndNursery {
        return if (connectivity.isOnline()) {
            remoteDataSource.addRoomAndNursery(room)
        } else
            throw Exception("no Internet")
    }

    override suspend fun deleteRoomOrNursery(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.deleteRoomOrNursery(id)
        } else
            throw Exception("no Internet")
    }

    override suspend fun doctorRegister(doctorRegisterRequest: DoctorRegisterRequest): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.doctorRegister(doctorRegisterRequest)
        } else
            throw Exception("no Internet")
    }

    override suspend fun ambulanceRegister(ambulanceRegisterRequest: AmbulanceRegisterRequest): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.ambulanceRegister(ambulanceRegisterRequest)
        } else
            throw Exception("no Internet")
    }

    override suspend fun hospitalRegister(hospitalRegisterRequest: HospitalRegisterRequest): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.hospitalRegister(hospitalRegisterRequest)
        } else
            throw Exception("no Internet")
    }

    override suspend fun userLogin(login: LoginRequest): TokenResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.userLogin(login)
        } else
            throw Exception("no Internet")
    }

    override suspend fun getCurrentPersonRequest(): PersonNotificationResponse {
        return if (connectivity.isOnline()) {
            remoteDataSource.getCurrentPersonRequest()
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun getPersonRequests(): List<PersonNotificationResponse> {
        return if (connectivity.isOnline()) {
            remoteDataSource.getPersonRequests()
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun confirmPersonRequest(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.confirmPersonRequest(id)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun completePersonRequest(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.completePersonRequest(id)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun cancelPersonRequest(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.cancelPersonRequest(id)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun deletePersonRequest(id: Int): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.deletePersonRequest(id)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun getDoctorDetails(doctorId: String): DoctorProfile? {
        return if (connectivity.isOnline()) {
            remoteDataSource.getDoctorDetails(doctorId)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun updateDoctorDetails(
        doctorId: String,
        doctorProfile: DoctorProfile
    ): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateDoctorDetails(
                doctorId,
                doctorProfile
            )
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun updateDoctorLocation(locationRequest: LocationRequest): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateDoctorLocation(locationRequest)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun getAmbulanceDetails(ambulanceId: String): AmbulanceProfile? {
        return if (connectivity.isOnline()) {
            remoteDataSource.getAmbulanceDetails(ambulanceId)
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun updateAmbulanceDetails(
        ambulanceId: String,
        ambulanceProfileDM: AmbulanceProfile
    ): Boolean {
        return if (connectivity.isOnline()) {
            remoteDataSource.updateAmbulanceDetails(
                ambulanceId,
                ambulanceProfileDM
            )
        } else {
            throw Exception("No internet connection")
        }
    }

    override suspend fun updateAmbulanceLocation(locationRequest: LocationRequest): Boolean {
        if (connectivity.isOnline()) {
            return remoteDataSource.updateAmbulanceLocation(locationRequest)
        } else {
            throw Exception("No internet connection")
        }
    }


    override suspend fun getRoute(start: LatLng, end: LatLng): Result<MapRouteDomain>  {
        return if (connectivity.isOnline()) {
            remoteDataSource.getRoute(start, end)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}
