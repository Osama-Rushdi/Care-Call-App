package com.example.carecallapp.data.api


import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital_profileDM.HospitalResponseDM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
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

    @PUT("/api/Hospital/UpdateHospitalDetails")
    suspend fun updateHospitalDetails(
        @Query("hospitalId") hospitalId: String,
        @Body hospitalResponseDM: HospitalResponseDM): Response<HospitalResponseDM>

}
