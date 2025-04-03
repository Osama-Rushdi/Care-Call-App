package com.example.carecallapp.data.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object ApiManager {
    const val BASE_URL = "http://localhost:5000/swagger/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getOkHttpResponse())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getOkHttpResponse(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor = loggingInterceptor)
            .build()
    }
     fun getWebServices(): WebServices = retrofit.create(WebServices::class.java)

}