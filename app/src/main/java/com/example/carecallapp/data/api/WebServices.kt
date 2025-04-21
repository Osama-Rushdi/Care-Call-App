package com.example.carecallapp.data.api


import com.example.carecallapp.data.model.hospital_accountsDM.PersonServiceDM
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WebServices {
    //    @GET("api/Service")
//    suspend  fun getService():List<ServiceResponseItemDM>
//
    @GET
    suspend fun getPeopleAccounts(
        @Url url: String,
        @Query("hospitalId") hospitalId: String
    ): List<PersonServiceDM>
}
