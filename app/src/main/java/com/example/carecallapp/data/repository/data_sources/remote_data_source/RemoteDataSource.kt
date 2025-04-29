package com.example.carecallapp.data.repository.data_sources.remote_data_source

import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse


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

}
