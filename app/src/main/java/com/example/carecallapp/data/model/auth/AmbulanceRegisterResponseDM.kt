package com.example.carecallapp.data.model.auth

import com.google.gson.annotations.SerializedName

data class AmbulanceRegisterResponseDM(
    @SerializedName("firstName")
    var firstName: String? = null,

    @SerializedName("lastName")
    var lastName: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("nationalId")
    var nationalId: String? = null,

    @SerializedName("gender")
    var gender: String? = null,

    @SerializedName("phone")
    var phone: Int = 0,

    @SerializedName("hospitalId")
    var hospitalId: String? = null,

    @SerializedName("confirmPassword")
    var confirmPassword: String? = null,

    @SerializedName("vehicleNumber")
    var vehicleNumber: String? = null,

    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("username")
    var username: String? = null
)
