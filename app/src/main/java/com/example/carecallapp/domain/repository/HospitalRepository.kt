package com.example.carecallapp.domain.repository

import com.example.carecallapp.domain.model.hospital_accounts.Accounts

interface HospitalRepository {
   fun getAccounts(sourceId: String?, token: String?):List<Accounts>
}