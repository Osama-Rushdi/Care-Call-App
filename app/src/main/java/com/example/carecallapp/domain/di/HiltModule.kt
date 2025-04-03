package com.example.carecallapp.domain.di

import RemoteDataSourceImpl
import com.example.carecallapp.data.repository.HospitalRepositoryImpl
import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.repository.HospitalRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HiltModule {

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindHospitalRepository(
        hospitalRepositoryImpl: HospitalRepositoryImpl
    ): HospitalRepository


}