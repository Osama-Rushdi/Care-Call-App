package com.example.carecallapp.data.repository.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.data.api.ApiManager
import com.example.carecallapp.domain.model.hospital_profile.HospitalResponse
import com.example.carecallapp.domain.use_cases.GetHospitalDetailsUseCase
import com.example.carecallapp.domain.use_cases.UpdateHospitalDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyProfileViewModel @Inject constructor(
    private val getHospitalDetailsUseCase: GetHospitalDetailsUseCase,
    private val updateHospitalDetailsUseCase: UpdateHospitalDetailsUseCase,
) : ViewModel() {
    val stateShow = MutableLiveData<ProfileStateShow>()

    fun showDetails(hospitalId: String = ApiManager.FAKE_HOSPITAL_ID) {
        stateShow.postValue(ProfileStateShow.Loading)
        viewModelScope.launch {
            try {
                val hospitalDetails = getHospitalDetailsUseCase.execute(hospitalId)
                stateShow.postValue(ProfileStateShow.IsSuccess(hospitalDetails))
                Log.d("kkk", "showDetails: $hospitalDetails")
            } catch (e: Exception) {
                stateShow.postValue(ProfileStateShow.ShowError(e.message.toString()))
                Log.d("kkk", "showError:${e.message} ")
            }
        }
    }

    fun updateDetails(
        hospitalId: String = ApiManager.FAKE_HOSPITAL_ID,
        hospitalResponse: HospitalResponse
    ) {
        stateShow.postValue(ProfileStateShow.Loading)
        viewModelScope.launch {
            try {
                val hospitalDetails =
                    updateHospitalDetailsUseCase.execute(hospitalId, hospitalResponse)
                stateShow.value=ProfileStateShow.IsSuccess(hospitalDetails)
            } catch (e: Exception) {
                stateShow.postValue(ProfileStateShow.ShowError(e.message.toString()))
            }
        }
    }
}

sealed class ProfileStateShow {
    data object Loading : ProfileStateShow()
    class IsSuccess(val hospitalDetails: HospitalResponse) : ProfileStateShow()
    data class ShowError(val errorMessage: String) : ProfileStateShow()
}
