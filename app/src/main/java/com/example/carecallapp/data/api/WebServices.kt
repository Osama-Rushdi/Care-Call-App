package com.example.carecallapp.data.api


import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital_services.BloodBagDM
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

    @GET
    suspend fun getPeopleAccounts(
        @Url url: String,
        @Query("hospitalId") hospitalId: String
    ): List<PersonServiceResponseDM>

    @PUT("api/Hospital/GetHospitalDetails")
    suspend fun getHospitalDetails(@Query("hospitalId") hospitalId: String): HospitalResponseDM?

    @PUT("api/Hospital/UpdateHospitalDetails")
    suspend fun updateHospitalDetails(
        @Query("hospitalId") hospitalId: String,
        @Body hospitalResponseDM: HospitalResponseDM): Response<Unit> //because not any return data

    @GET("api/BloodBank")
    suspend fun getAll(): List<BloodBagDM>

    @GET("api/BloodBank/{id}")
    suspend fun getById(@Path("id") id: Int): BloodBagDM

    @POST("api/BloodBank")
    suspend fun addBlood(@Body blood: BloodBagDM): Response<BloodBagDM>

    @PUT("api/BloodBank")
    suspend fun updateBlood(
        @Query("id") id: Int,
        @Body blood: BloodBagDM
    ): Response<Unit>

    @DELETE("api/BloodBank")
    suspend fun deleteBlood(@Query("id") id: Int): Response<Unit>



}
