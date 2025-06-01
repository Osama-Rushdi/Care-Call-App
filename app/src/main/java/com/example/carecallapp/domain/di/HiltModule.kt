package com.example.carecallapp.domain.di

import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSourceImpl
import com.example.carecallapp.data.repository.repository_imp.MyRepositoryImpl
import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.repository.MyRepository
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
        hospitalRepositoryImpl: MyRepositoryImpl
    ): MyRepository
}