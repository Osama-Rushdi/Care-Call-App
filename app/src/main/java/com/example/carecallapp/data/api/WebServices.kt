package com.example.carecallapp.data.api


import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("")
    suspend  fun getAccounts(
      @Query("") categoryName: String
    )


}