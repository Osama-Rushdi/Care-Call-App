package com.example.carecallapp.data.repository.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.domain.model.hospital_content.ServiceRequest
import com.example.carecallapp.domain.model.hospital_content.ServiceResponse
import com.example.carecallapp.domain.model.hospital_content.ServiceType
import com.example.carecallapp.domain.use_cases.AddServiceUseCase
import com.example.carecallapp.domain.use_cases.DeleteServiceUseCase
import com.example.carecallapp.domain.use_cases.GetAllServicesUseCase
import com.example.carecallapp.domain.use_cases.SearchServiceByNameUseCase
import com.example.carecallapp.domain.use_cases.UpdateServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyServicesViewModel @Inject constructor(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val updateServiceUseCase: UpdateServiceUseCase,
    private val addServiceUseCase: AddServiceUseCase,
    private val deleteServiceUseCase: DeleteServiceUseCase,
    private val searchServiceByNameUseCase: SearchServiceByNameUseCase

) : ViewModel() {
    val stateShow = MutableLiveData<ServiceStateShow>()
    val initServiceAdapter = MutableLiveData<List<ServiceResponse>>()

    fun searchServiceByNameUseCase(name:String) {
        viewModelScope.launch {
            try {
                val service = getAllServicesUseCase.execute()
                searchServiceByNameUseCase.execute(name, service)
                stateShow.postValue(ServiceStateShow.IsSearchSuccess(true))

            } catch (e: Exception) {
                Log.e("kkk", "Error in getAllServicesUseCase: ${e.message}", e)
                stateShow.postValue(ServiceStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun getAllServices() {
        viewModelScope.launch {
            stateShow.postValue(ServiceStateShow.Loading)
            try {
                val service = getAllServicesUseCase.execute()
                if (service.isEmpty())
                    stateShow.postValue(ServiceStateShow.IsFound)
                else {
                    Log.d("kkk", "getAllServicesUseCase: $service")
                    stateShow.postValue(ServiceStateShow.IsSuccess(service))
                    initServiceAdapter.postValue(service)
                }
            } catch (e: Exception) {
                Log.e("kkk", "Error in getAllServicesUseCase: ${e.message}", e)
                stateShow.postValue(ServiceStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun addService(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            stateShow.postValue(ServiceStateShow.Loading)
            try {
                val service = addServiceUseCase.execute(serviceRequest)
                Log.d("kkk", "add service UseCase: $service")
                stateShow.postValue(ServiceStateShow.IsAddSuccess(service))

            } catch (e: Exception) {
                Log.e("kkk", "Error in add serviceUseCase: ${e.message}", e)
                stateShow.postValue(ServiceStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun updateService(id: Int, serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            try {
                val service = updateServiceUseCase.execute(id, serviceRequest)
                Log.d("kkk", "update serviceUseCase: $service")
                stateShow.postValue(ServiceStateShow.IsUpdateSuccess(service))
            } catch (e: Exception) {
                Log.e("kkk", "Error in updateService: ${e.message}", e)
                stateShow.postValue(ServiceStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun deleteService(id: Int) {
        viewModelScope.launch {
            stateShow.postValue(ServiceStateShow.Loading)
            try {
                val service = deleteServiceUseCase.execute(id)
                Log.d("kkk", "deleteServiceUseCase: $service")
                stateShow.postValue(ServiceStateShow.IsDeleteSuccess(service))
                getAllServices()
            } catch (e: Exception) {
                Log.e("kkk", "Error in deleteServiceUseCase: ${e.message} $id")
                stateShow.postValue(ServiceStateShow.ShowError(e.message.toString()))
            }
        }
    }
}

sealed class ServiceStateShow {

    class IsAddSuccess(val serviceResponse: ServiceResponse) : ServiceStateShow()
    class IsDeleteSuccess(val isSuccess: Boolean) : ServiceStateShow()
    class IsUpdateSuccess(val isSuccess: Boolean) : ServiceStateShow()
    class IsSuccess(val services: List<ServiceResponse>) : ServiceStateShow()
    data class ShowError(val errorMessage: String) : ServiceStateShow()
    class IsSearchSuccess(val success: Boolean) : ServiceStateShow()
    data object IsFound : ServiceStateShow()
    data object Loading : ServiceStateShow()

}
