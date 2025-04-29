package com.example.carecallapp.data.api

import android.util.Log
import okhttp3.Interceptor

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d("AuthInterceptor", "Request URL: ${chain.request().url}")
        Log.d("AuthInterceptor", "Authorization Header: Bearer $token")

        return chain.proceed(newRequest)
    }
}
