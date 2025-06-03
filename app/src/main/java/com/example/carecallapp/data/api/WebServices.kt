package com.example.carecallapp.data.api

import com.example.carecallapp.data.model.PersonService.LocationRequestDM
import com.example.carecallapp.data.model.PersonService.MapRouteResponseDM
import com.example.carecallapp.data.model.PersonService.PersonNotificationResponseDM
import com.example.carecallapp.data.model.PersonService.ambulance.AmbulanceProfileDM
import com.example.carecallapp.data.model.PersonService.doctor.DoctorProfileDM
import com.example.carecallapp.data.model.auth.AmbulanceRegisterRequestDM
import com.example.carecallapp.data.model.auth.DoctorRegisterRequestDM
import com.example.carecallapp.data.model.auth.HospitalRegisterResponseDM
import com.example.carecallapp.data.model.auth.LoginRequestDM
import com.example.carecallapp.data.model.auth.TokenResponseDM
import com.example.carecallapp.data.model.hospital.hospital_accountsDM.PersonServiceResponseDM
import com.example.carecallapp.data.model.hospital.hospital_notification.HospitalNotificationResponseDM
import com.example.carecallapp.data.model.hospital.hospital_profileDM.HospitalResponseDM
import com.example.carecallapp.data.model.hospital.hospital_services.BloodBagDM
import com.example.carecallapp.data.model.hospital.hospital_services.RoomAndNurseryResponseDM
import com.example.carecallapp.data.model.hospital.hospital_services.ServiceRequestDM
import com.example.carecallapp.data.model.hospital.hospital_services.ServiceResponseDM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WebServices {
    //----------------------Authentication-------------------------

    @POST("api/Account/Doctor-Register")
    suspend fun doctorRegister(@Body register: DoctorRegisterRequestDM): Response<TokenResponseDM>

    @POST("api/Account/Amulance-Register")
    suspend fun ambulanceRegister(@Body register: AmbulanceRegisterRequestDM): Response<TokenResponseDM>

    @POST("api/Account/Hopital-Register")
    suspend fun hospitalRegister(@Body register: HospitalRegisterResponseDM): Response<TokenResponseDM>

    @POST("api/Account/Login")
    suspend fun userLogin(@Body login: LoginRequestDM): Response<TokenResponseDM>

    //-----------------------HOSPITAL------------------------------

    //ambulance and doctor accounts
    @GET
    suspend fun getPeopleAccounts(
        @Url url: String,
        @Query("hospitalId") hospitalId: String
    ): List<PersonServiceResponseDM>

    //profile
    @PUT("api/Hospital/GetHospitalDetails")
    suspend fun getHospitalDetails(@Query("hospitalId") hospitalId: String): HospitalResponseDM?

    @PUT("api/Hospital/UpdateHospitalDetails")
    suspend fun updateHospitalDetails(
        @Query("hospitalId") hospitalId: String,
        @Body hospitalResponseDM: HospitalResponseDM
    ): Response<Unit> //because not any return data

    //Services

    @GET("api/Service")
    suspend fun getAllServices(): List<ServiceResponseDM>

    @POST("api/Service")
    suspend fun addService(@Body service: ServiceRequestDM): Response<ServiceResponseDM>

    @DELETE("api/Service")
    suspend fun deleteService(@Query("id") id: Int): Response<Unit>

    @PUT("api/Service")
    suspend fun updateService(
        @Query("id") id: Int,
        @Body service: ServiceRequestDM
    ): Response<Unit>


    //blood bank
    @GET("api/BloodBank")
    suspend fun getAllBloodBags(): List<BloodBagDM>

    @GET("api/BloodBank/{id}")
    suspend fun getBloodById(@Path("id") id: Int): BloodBagDM

    @POST("api/BloodBank")
    suspend fun addBlood(@Body blood: BloodBagDM): Response<BloodBagDM>

    @PUT("api/BloodBank")
    suspend fun updateBlood(
        @Query("id") id: Int,
        @Body blood: BloodBagDM
    ): Response<Unit>

    @DELETE("api/BloodBank")
    suspend fun deleteBlood(@Query("id") id: Int): Response<Unit>

    //room and nursery
    @GET("api/Bed")
    suspend fun getAllRoomsAndNurseries(): List<RoomAndNurseryResponseDM>

    @GET("api/Bed/{id}")
    suspend fun getRoomAndNurseryById(@Path("id") id: Int): RoomAndNurseryResponseDM

    @POST("api/Bed")
    suspend fun addRoomAndNursery(@Body blood: RoomAndNurseryResponseDM): Response<RoomAndNurseryResponseDM>

    @DELETE("api/Bed")
    suspend fun deleteRoomOrNursery(@Query("id") id: Int): Response<Unit>

    //hospital requests
    @GET("api/Request/hospital-requests")
    suspend fun getHospitalRequests(): Response<List<HospitalNotificationResponseDM>>


    //------------------DOCTOR AND AMBULANCE REQUESTS-----------------------

    @GET("api/Request/Ambulance&Doctor-CurrentRequest")
    suspend fun getCurrentPersonRequest(): Response<PersonNotificationResponseDM>


    @GET("api/Request/Ambulance&Doctor-requests")
    suspend fun getPersonRequests(): Response<List<PersonNotificationResponseDM>>

    @PUT("api/Request/confirm-request")
    suspend fun confirmPersonRequest(
        @Query("id") id: Int,
    ): Response<Unit>

    @PUT("api/Request/complete-request")
    suspend fun completePersonRequest(
        @Query("id") id: Int,
    ): Response<Unit>

    @PUT("api/Request/Cancel-request")
    suspend fun cancelPersonRequest(
        @Query("id") id: Int
    ): Response<Unit>

    @DELETE("api/Request/Delete-request")
    suspend fun deletePersonRequest(
        @Query("id") id: Int
    ): Response<Unit>


    //-------------------DOCTOR------------------------------

    //profile
    @PUT("api/Doctor/GetDoctorDetails")
    suspend fun getDoctorDetails(@Query("doctorId") doctorId: String): Response<DoctorProfileDM?>

    @PUT("api/Doctor/UpdateDoctorDetails")
    suspend fun updateDoctorDetails(
        @Query("doctorId") doctorId: String,
        @Body doctorProfileDM: DoctorProfileDM
    ): Response<Unit> //because not any return data

    @PUT("api/Doctor/UpdateLocation")
    suspend fun updateDoctorLocation(@Body locationRequest: LocationRequestDM): Response<Unit>
    //-------------------AMBULANCE------------------------------

    @PUT("api/Ambulance/GetAmbulanceDetails")
    suspend fun getAmbulanceDetails( @Query("ambulanceId") ambulanceId: String): Response<AmbulanceProfileDM?>

    @PUT("api/Ambulance/UpdateAmbulanceDetails")
    suspend fun updateAmbulanceDetails(
        @Query("ambulanceId") ambulanceId: String,
        @Body doctorProfileDM: AmbulanceProfileDM
    ): Response<Unit> //because not any return data

    @PUT("api/Ambulance/UpdateLocation")
    suspend fun updateAmbulanceLocation(@Body locationRequest: LocationRequestDM): Response<Unit>


}
