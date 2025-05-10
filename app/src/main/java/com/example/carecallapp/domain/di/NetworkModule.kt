package com.example.carecallapp.domain.di

import android.content.Context
import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.api.ApiManager.BASE_URL
import com.example.carecallapp.data.api.AuthInterceptor
import com.example.carecallapp.data.repository.Connectivity
import com.example.carecallapp.ui.utils.Constants
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

        val getToken = context.getSharedPreferences(Constants.SHARED_TOKEN_NAME, Context.MODE_PRIVATE)
        val token =getToken.getString(Constants.SHARED_TOKEN_KEY, "")

        val authInterceptor = AuthInterceptor(token?:"")

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}