package com.example.carecallapp.data.repository.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.domain.model.auth.AmbulanceRegisterRequest
import com.example.carecallapp.domain.model.auth.DoctorRegisterRequest
import com.example.carecallapp.domain.model.auth.HospitalRegisterRequest
import com.example.carecallapp.domain.model.auth.LoginRequest
import com.example.carecallapp.domain.model.auth.TokenResponse
import com.example.carecallapp.domain.use_cases.AmbulanceRegisterUseCase
import com.example.carecallapp.domain.use_cases.DoctorRegisterUseCase
import com.example.carecallapp.domain.use_cases.HospitalRegisterUseCase
import com.example.carecallapp.domain.use_cases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyAuthViewModel @Inject constructor(
    private val doctorRegisterUseCase: DoctorRegisterUseCase,
    private val ambulanceRegisterUseCase: AmbulanceRegisterUseCase,
    private val hospRegisterUseCase: HospitalRegisterUseCase,
    private val loginUseCase: LoginUseCase,

    ) : ViewModel() {

    val stateShow = MutableLiveData<AuthStateShow>()

    fun doctorRegister(doctorRegisterRequest: DoctorRegisterRequest) {
        stateShow.postValue(AuthStateShow.Loading)
        viewModelScope.launch {
            try {
                val doctorRegister = doctorRegisterUseCase.execute(doctorRegisterRequest)
                if (doctorRegister)
                    stateShow.postValue(AuthStateShow.IsRegisterSuccess(true))
                Log.d("kkk", "doctorRegister: $doctorRegister")
            } catch (e: Exception) {
                stateShow.postValue(AuthStateShow.ShowError(e.message.toString()))
                Log.d("kkk", "showError:${e.message} ")
            }
        }
    }

    fun ambulanceRegister(ambulanceRegisterResponse: AmbulanceRegisterRequest) {
        stateShow.postValue(AuthStateShow.Loading)
        viewModelScope.launch {
            try {
                val ambulanceRegister = ambulanceRegisterUseCase.execute(ambulanceRegisterResponse)
                if (ambulanceRegister)
                    stateShow.postValue(AuthStateShow.IsRegisterSuccess(true))
                Log.d("kkk", "ambulanceRegister: $ambulanceRegister")
            } catch (e: Exception) {
                stateShow.postValue(AuthStateShow.ShowError(e.message.toString()))
                Log.d("kkk", "showError:${e.message}")
            }
        }
    }

    fun hospitalRegister(hospitalRegisterResponse: HospitalRegisterRequest) {
        stateShow.postValue(AuthStateShow.Loading)
        viewModelScope.launch {
            try {
                val hospitalRegister = hospRegisterUseCase.execute(hospitalRegisterResponse)
                if (hospitalRegister)
                    stateShow.postValue(AuthStateShow.IsRegisterHospitalSuccess(true))
                Log.d("kkk", "hospitalRegister: $hospitalRegister")
            } catch (e: Exception) {
                stateShow.postValue(AuthStateShow.ShowError(e.message.toString()))
                Log.d("kkk", "showError:${e.message} ")
            }
        }
    }

    fun userLogIn(loginRequest: LoginRequest) {
        stateShow.postValue(AuthStateShow.Loading)
        viewModelScope.launch {
            try {
                val userLogin = loginUseCase.execute(loginRequest)
                stateShow.postValue(AuthStateShow.IsSuccessLogin(userLogin))
                Log.d("kkk", "userLogin: $userLogin")
            } catch (e: Exception) {
                stateShow.postValue(AuthStateShow.ShowError(e.message.toString()))
                Log.d("kkk", "showError:${e.message} ")
            }
        }
    }

}


sealed class AuthStateShow {
    data object Loading : AuthStateShow()
    class IsRegisterSuccess(val isSuccess: Boolean) : AuthStateShow()
    class IsRegisterHospitalSuccess(val isSuccess: Boolean) : AuthStateShow()
    data class ShowError(val errorMessage: String) : AuthStateShow()
    class IsSuccessLogin(val userLogin: TokenResponse) : AuthStateShow() {

    }
}
