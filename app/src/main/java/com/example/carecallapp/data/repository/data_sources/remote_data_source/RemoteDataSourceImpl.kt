package com.example.carecallapp.data.repository.data_sources.remote_data_source

import android.content.Context
import android.util.Log
import com.example.carecallapp.R
import com.example.carecallapp.data.api.MapRouteApiService
import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.mappers.toBloodBag
import com.example.carecallapp.data.mappers.toBloodDM
import com.example.carecallapp.data.mappers.toDataModel
import com.example.carecallapp.data.mappers.toDomain
import com.example.carecallapp.data.mappers.toDomainModel
import com.example.carecallapp.data.mappers.toRoomAndNursery
import com.example.carecallapp.data.mappers.toHospitalResponse
import com.example.carecallapp.data.mappers.toHospitalResponseDM
import com.example.carecallapp.data.mappers.toLoginDM
import com.example.carecallapp.data.mappers.toLoginResponse
import com.example.carecallapp.data.mappers.toPeopleService
import com.example.carecallapp.data.mappers.toRoomAndNurseryDM
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
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val webServices: WebServices, @ApplicationContext val context: Context,
    private val mapRouteApiService: MapRouteApiService
) : RemoteDataSource {

    //get accounts doctor and ambulance
    override suspend fun getPeopleAccounts(
        accountType: String, hospitalId: String
    ): List<PersonServiceResponse> {
        return webServices.getPeopleAccounts(accountType, hospitalId = hospitalId)
            .map { it.toPeopleService() }
    }

    //profile get-edit
    override suspend fun getHospitalDetails(hospitalId: String): HospitalResponse =
        webServices.getHospitalDetails(hospitalId)!!.toHospitalResponse()

    override suspend fun updateHospitalDetails(
        hospitalId: String, hospitalResponse: HospitalResponse
    ): HospitalResponse {
        val response =
            webServices.updateHospitalDetails(hospitalId, hospitalResponse.toHospitalResponseDM())
        if (response.isSuccessful) {
            return hospitalResponse // لأنه فعليًا اتحدث بنجاح ومفيش داتا جديدة هترجع
        } else {
            throw Exception("Update failed with code: ${response.code()}")
        }
    }


    //services
    override suspend fun getAllServices(): List<ServiceResponse> =
        webServices.getAllServices().map { it.toDomainModel() }

    override suspend fun addService(service: ServiceRequest): ServiceResponse {
        val response = webServices.addService(service.toDataModel())
        return if (response.isSuccessful) response.body()!!.toDomainModel()
        else throw Exception("add Service failed with code: ${response.code()}")
    }

    override suspend fun deleteService(id: Int): Boolean {
        val response = webServices.deleteService(id)
        return if (response.isSuccessful) true
        else throw Exception("delete Service failed with code: ${response.code()}")
    }

    override suspend fun updateService(id: Int, service: ServiceRequest): Boolean {
        val response = webServices.updateService(id, service.toDataModel())
        return if (response.isSuccessful) true
        else {
            throw Exception("update blood failed with code: ${response.code()}")
        }
    }

    //blood bank
    override suspend fun getBloodAll(): List<BloodBag> =
        webServices.getAllBloodBags().map { it.toBloodBag() }

    override suspend fun addBlood(blood: BloodBag): BloodBag {
        val response = webServices.addBlood(blood.toBloodDM())
        return if (response.isSuccessful) blood
        else throw Exception("add blood failed with code: ${response.code()}")
    }

    override suspend fun updateBlood(id: Int, blood: BloodBag): BloodBag {
        val response = webServices.updateBlood(id, blood.toBloodDM())
        return if (response.isSuccessful) blood
        else {
            throw Exception("update blood failed with code: ${response.code()}")
        }
    }

    override suspend fun deleteBlood(id: Int): Boolean {
        val response = webServices.deleteBlood(id)
        return if (response.isSuccessful) true
        else throw Exception("delete blood failed with code: ${response.code()}")
    }

    override suspend fun getBloodById(id: Int): BloodBag = webServices.getBloodById(id).toBloodBag()

    //room and nursery
    override suspend fun getAllRoomsAndNurseries(): List<RoomAndNursery> {
        return webServices.getAllRoomsAndNurseries().map { it.toRoomAndNursery() }
    }

    override suspend fun getRoomAndNurseryById(id: Int): RoomAndNursery =
        webServices.getRoomAndNurseryById(id).toRoomAndNursery()

    override suspend fun addRoomAndNursery(room: RoomAndNursery): RoomAndNursery {
        val response = webServices.addRoomAndNursery(room.toRoomAndNurseryDM())
        return if (response.isSuccessful) room
        else throw Exception("add blood failed with code: ${response.code()}")
    }

    override suspend fun deleteRoomOrNursery(id: Int): Boolean {
        val response = webServices.deleteRoomOrNursery(id)
        Log.d("kkk", "deleteRoomOrNursery:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("delete Bed failed with code: ${response.code()}")
        }
    }

    override suspend fun doctorRegister(doctorRegisterRequest: DoctorRegisterRequest): Boolean {
        val response = webServices.doctorRegister(doctorRegisterRequest.toDataModel())
        Log.d("kkk", "doctorRegister:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("doctor Register failed with code: ${response.code()}")
        }
    }

    override suspend fun ambulanceRegister(ambulanceRegisterRequest: AmbulanceRegisterRequest): Boolean {
        val response = webServices.ambulanceRegister(ambulanceRegisterRequest.toDataModel())
        Log.d("kkk", "ambulanceRegister:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("ambulance Register failed with code: ${response.code()}")
        }
    }

    override suspend fun hospitalRegister(hospitalRegisterRequest: HospitalRegisterRequest): Boolean {
        val response = webServices.hospitalRegister(hospitalRegisterRequest.toDataModel())
        Log.d("kkk", "hospital Register:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("hospital Register failed with code: ${response.code()}")
        }
    }


    override suspend fun userLogin(login: LoginRequest): TokenResponse {
        val response = webServices.userLogin(login.toLoginDM())
        Log.d("kkk", "userLogin:$response ")
        if (response.isSuccessful) {
            return response.body()!!.toLoginResponse()
        } else {
            when (response.code()) {
                400 -> throw Exception(context.getString(R.string.email_or_password_is_incorrect))
                401 -> throw Exception(context.getString(R.string.you_are_not_authorized_to_log_in))
                500 -> throw Exception(context.getString(R.string.server_error_please_try_again_later))
                else -> throw Exception(
                    " ${context.getString(R.string.unexpected_error)} ${response.code()}"
                )
            }
        }
    }

    override suspend fun getCurrentPersonRequest(): PersonNotificationResponse {
        val response = webServices.getCurrentPersonRequest()
        Log.d("kkk", "getCurrentPersonRequest:$response ")
        if (response.isSuccessful) {
            return response.body()!!.toDomain()
        } else
            throw Exception("get Current Person Request failed with code: ${response.code()}")
    }

    override suspend fun getPersonRequests(): List<PersonNotificationResponse> {
        val response = webServices.getPersonRequests()
        Log.d("kkk", "get PersonRequests:$response ")
        if (response.isSuccessful) {
            return response.body()!!.map { it.toDomain() }
        } else
            throw Exception("get Person Requests failed with code: ${response.code()}")
    }

    override suspend fun confirmPersonRequest(id: Int): Boolean {
        val response = webServices.confirmPersonRequest(id)
        Log.d("kkk", "confirm Person Request:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("confirm Person Request: ${response.errorBody()}")
        }
    }

    override suspend fun completePersonRequest(id: Int): Boolean {
        val response = webServices.completePersonRequest(id)
        Log.d("kkk", "complete Person Request:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            val errorJson = response.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJson ?: "{}").optString("error", "Unknown error")
            } catch (e: Exception) {
                "Error parsing error message"
            }

            Log.e("kkk", "Error: $errorMessage")
            throw Exception(errorMessage)
        }
    }

    override suspend fun cancelPersonRequest(id: Int): Boolean {
        val response = webServices.cancelPersonRequest(id)
        Log.d("kkk", "cancel Person Request:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("cancel Person Request: ${response.code()}")
        }
    }


    override suspend fun getHospitalRequests(): List<HospitalNotificationResponse> {
        val response = webServices.getHospitalRequests()
        Log.d("kkk", "get Hospital Requests:$response ")
        if (response.isSuccessful) {
            return response.body()!!.map { it.toDomain() }
        } else
            throw Exception("get Hospital Requests failed with code: ${response.code()}")

    }

    //--------------DOCTOR------------------
    override suspend fun getDoctorDetails(doctorId: String): DoctorProfile? {
        val doctorProfile = webServices.getDoctorDetails(doctorId)
        if (doctorProfile.isSuccessful) {
            return doctorProfile.body()?.toDomainModel()
        } else {
            throw Exception("get Doctor Details failed with code: ${doctorProfile.code()}")
        }
    }

    override suspend fun updateDoctorDetails(
        doctorId: String,
        doctorProfile: DoctorProfile
    ): Boolean {
        val response = webServices.updateDoctorDetails(doctorId, doctorProfile.toDataModel())
        return if (response.isSuccessful) {
            true
        } else {
            throw Exception("update Doctor Details failed with code: ${response.code()}")
        }
    }

    override suspend fun updateDoctorLocation(locationRequest: LocationRequest): Boolean {
        val location = webServices.updateDoctorLocation(locationRequest.toDataModel())
        return if (location.isSuccessful) {
            true
        } else {
            throw Exception("update Doctor Location failed with code: ${location.code()}")
        }
    }

    //--------------AMBULANCE------------------

    override suspend fun getAmbulanceDetails(ambulanceId: String): AmbulanceProfile? {
        val ambulanceProfile = webServices.getAmbulanceDetails(ambulanceId)
        if (ambulanceProfile.isSuccessful) {
            return ambulanceProfile.body()?.toDomainModel()
        } else {
            throw Exception("get ambulance Details failed with code: ${ambulanceProfile.code()}")
        }
    }

    override suspend fun updateAmbulanceDetails(
        ambulanceId: String,
        ambulanceProfile: AmbulanceProfile
    ): Boolean {
        val response =
            webServices.updateAmbulanceDetails(ambulanceId, ambulanceProfile.toDataModel())
        return if (response.isSuccessful) {
            true
        } else {
            throw Exception("update Doctor Details failed with code: ${response.code()}")
        }
    }

    override suspend fun updateAmbulanceLocation(locationRequest: LocationRequest): Boolean {
        val location = webServices.updateAmbulanceLocation(locationRequest.toDataModel())
        return if (location.isSuccessful) {
            true
        } else {
            throw Exception("update ambulance Location failed with code: ${location.code()}")
        }
    }

    // Map Route
    override suspend fun getRoute(start: LatLng, end: LatLng): Result<MapRouteDomain> {
        return try {
            val domainModel = mapRouteApiService.getRoute(
                start.longitude, start.latitude,
                end.longitude, end.latitude
            ).toDomain()

            if (domainModel.path.isNotEmpty()) {
                Result.success(domainModel)
            } else {
                Result.failure(Exception("No route found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
