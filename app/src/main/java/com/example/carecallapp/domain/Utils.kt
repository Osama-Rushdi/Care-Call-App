package com.example.carecallapp.domain

object Utils {
}
object Types{
    fun peopleTypeUrl(ifDoctor:Boolean):String=if (ifDoctor) "api/Doctor/GetDoctorsOfHospital" else "api/Ambulance/GetAmbulancesOfHospital"

}