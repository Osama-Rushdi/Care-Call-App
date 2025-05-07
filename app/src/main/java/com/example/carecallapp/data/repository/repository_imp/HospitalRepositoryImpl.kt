package com.example.carecallapp.data.repository.repository_imp
import com.example.carecallapp.data.repository.Connectivity
import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.repository.HospitalRepository
import javax.inject.Inject


class HospitalRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val connectivity: Connectivity,
) : HospitalRepository {

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
}
