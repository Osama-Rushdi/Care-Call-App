package com.example.carecallapp.data.repository.data_sources.remote_data_source

import android.content.Context
import android.util.Log
import com.example.carecallapp.R
import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.mappers.toBloodBag
import com.example.carecallapp.data.mappers.toBloodDM
import com.example.carecallapp.data.mappers.toDataModel
import com.example.carecallapp.data.mappers.toDomainModel
import com.example.carecallapp.data.mappers.toRoomAndNursery
import com.example.carecallapp.data.mappers.toHospitalResponse
import com.example.carecallapp.data.mappers.toHospitalResponseDM
import com.example.carecallapp.data.mappers.toLoginDM
import com.example.carecallapp.data.mappers.toLoginResponse
import com.example.carecallapp.data.mappers.toPeopleService
import com.example.carecallapp.data.mappers.toRoomAndNurseryDM
import com.example.carecallapp.domain.model.auth.AmbulanceRegisterResponse
import com.example.carecallapp.domain.model.auth.DoctorRegisterResponse
import com.example.carecallapp.domain.model.auth.HospitalRegisterResponse
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.LoginResponse
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital_content.ServiceResponse
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val webServices: WebServices, @ApplicationContext val context: Context
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
        return if (response.isSuccessful)  response.body()!!.toDomainModel()
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

    override suspend fun doctorRegister(doctorRegisterResponse: DoctorRegisterResponse): Boolean {
        val response = webServices.doctorRegister(doctorRegisterResponse.toDataModel())
        Log.d("kkk", "doctorRegister:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("doctor Register failed with code: ${response.code()}")
        }
    }

    override suspend fun ambulanceRegister(ambulanceRegisterResponse: AmbulanceRegisterResponse): Boolean {
        val response = webServices.ambulanceRegister(ambulanceRegisterResponse.toDataModel())
        Log.d("kkk", "ambulanceRegister:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("ambulance Register failed with code: ${response.code()}")
        }
    }

    override suspend fun hospitalRegister(hospitalRegisterResponse: HospitalRegisterResponse): Boolean {
        val response = webServices.hospitalRegister(hospitalRegisterResponse.toDataModel())
        Log.d("kkk", "hospital Register:$response ")
        if (response.isSuccessful) {
            return true
        } else {
            throw Exception("hospital Register failed with code: ${response.code()}")
        }
    }


    override suspend fun userLogin(login: LoginRequest): LoginResponse {
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
}
