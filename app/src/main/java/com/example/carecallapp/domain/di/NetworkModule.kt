package com.example.carecallapp.domain.di

import android.content.Context
import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.api.ApiManager.BASE_URL
import com.example.carecallapp.data.api.AuthInterceptor
import com.example.carecallapp.data.repository.Connectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGetWebServices(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Provides
    fun provideGetRetrofit(
        getOkHttpResponse: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(getOkHttpResponse)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    fun gsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideGetConnectivity(@ApplicationContext context: Context): Connectivity =
        Connectivity(context)

    @Provides
    fun provideGetOkHttpResponse(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        //  val token = sharedPreferences.getString("token", "") ?: ""
        val token =
            ("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6ImQxNGY1ODM2LWNhODEtNGE0ZS05MzAwLTgyYTQyNjkzM2ZmMiIsImlkIjoiZDE0ZjU4MzYtY2E4MS00YTRlLTkzMDAtODJhNDI2OTMzZmYyIiwibmFtZSI6Imhvc3BpdGFsIiwianRpIjoiY2IxY2MzNzUtZWYxYS00YjU0LWFiMjktMGE2MzMwOWZjOWU0IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiSG9zcGl0YWwiLCJleHAiOjE3NTAzOTcxODksImlzcyI6IkNhcmUgQ2FsbCJ9.9N3Z8qxanMUwhYTT2SvI9Xqyai8DoKjVuCI7VCzBhmA").trimMargin()
        val authInterceptor = AuthInterceptor(token)

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}