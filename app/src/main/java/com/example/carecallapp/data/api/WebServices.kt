package com.example.carecallapp.data.api

import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital_services.BloodBagDM
import com.example.carecallapp.data.model.hospital_services.RoomAndNurseryDM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WebServices {

    //ambulance and doctor accounts
    @GET
    suspend fun getPeopleAccounts(
        @Url url: String,
        @Query("hospitalId") hospitalId: String
    ): List<PersonServiceResponseDM>

    //profile
    @PUT("api/Hospital/GetHospitalDetails")
    suspend fun getHospitalDetails(@Query("hospitalId") hospitalId: String): HospitalResponseDM?

    @PUT("api/Hospital/UpdateHospitalDetails")
    suspend fun updateHospitalDetails(
        @Query("hospitalId") hospitalId: String,
        @Body hospitalResponseDM: HospitalResponseDM
    ): Response<Unit> //because not any return data

    //services
//    @GET("api/Services")
//    suspend fun getAllServices(): List<BloodBagDM>

    //blood bank
    @GET("api/BloodBank")
    suspend fun getAllBloodBags(): List<BloodBagDM>

    @GET("api/BloodBank/{id}")
    suspend fun getBloodById(@Path("id") id: Int): BloodBagDM

    @POST("api/BloodBank")
    suspend fun addBlood(@Body blood: BloodBagDM): Response<BloodBagDM>

    @PUT("api/BloodBank")
    suspend fun updateBlood(
        @Query("id") id: Int,
        @Body blood: BloodBagDM
    ): Response<Unit>

    @DELETE("api/BloodBank")
    suspend fun deleteBlood(@Query("id") id: Int): Response<Unit>

    //room and nursery
    @GET("api/Bed")
    suspend fun getAllRoomsAndNurseries(): List<RoomAndNurseryDM>

    @GET("api/Bed/{id}")
    suspend fun getRoomAndNurseryById(@Path("id") id: Int): RoomAndNurseryDM

    @POST("api/Bed")
    suspend fun addRoomAndNursery(@Body blood: RoomAndNurseryDM): Response<RoomAndNurseryDM>

    @DELETE("api/Bed")
    suspend fun deleteRoomOrNursery(@Query("id") id: Int): Response<Unit>

}
