package com.example.carecallapp.data.repository.data_sources.remote_data_source

import com.example.carecallapp.domain.model.hospital_accounts.Accounts


interface RemoteDataSource {
    fun getAccounts(sourceId: String?, token: String?):List<Accounts>
  }