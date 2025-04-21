package com.example.carecallapp.data.model.hospital_accountsDM

import com.google.gson.annotations.SerializedName

data class PersonServiceResponseDM(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("userName")
    val userName: String?,
    @field:SerializedName("phoneNumber")
    val phoneNumber: String?
)
