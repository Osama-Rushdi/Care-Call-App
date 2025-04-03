package com.example.carecallapp.domain.di

import android.content.Context
import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.api.ApiManager.BASE_URL
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
    fun provideGetOkHttpResponse(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor = loggingInterceptor).build()
    }


}