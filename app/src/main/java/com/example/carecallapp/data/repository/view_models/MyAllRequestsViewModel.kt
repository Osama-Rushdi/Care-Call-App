package com.example.carecallapp.data.repository.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.domain.model.PersonService.PersonNotificationResponse
import com.example.carecallapp.domain.model.PersonService.RequestStatus
import com.example.carecallapp.domain.model.hospital.hospital_notification.HospitalNotificationResponse
import com.example.carecallapp.domain.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPersonRequestsViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {

    val requestState = MutableLiveData<RequestsStateShow>()
    val initCompleteRequestsAdapter = MutableLiveData<List<Any?>?>()
    val initCanceledRequestsAdapter = MutableLiveData<List<Any?>?>()
    val initPendingRequestsAdapter = MutableLiveData<List<Any?>?>()
    val initConfirmedRequestsAdapter = MutableLiveData<List<Any?>?>()



    private var lastCurrentRequest: PersonNotificationResponse? = null

    // Store all requests to filter them later
    private var allPersonRequests: List<PersonNotificationResponse> = emptyList()
    private var allHospitalRequests: List<HospitalNotificationResponse> = emptyList()
    private var pollingJob: Job? = null

    fun startPollingRequests() {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            while (isActive) {
                getCurrentRequest()
                delay(10000)
            }
        }
    }

    fun stopPollingRequests() {
        pollingJob?.cancel()
    }

    fun getPersonRequests() {
        viewModelScope.launch {
            requestState.postValue(RequestsStateShow.Loading)
            try {
                val requests = repository.getPersonRequests()
                if (requests.isEmpty()) {
                    requestState.postValue(RequestsStateShow.IsNotFound)
                } else {
                    allPersonRequests = requests
                    filterRequestsByStatus(null) // null = All
                    requestState.postValue(RequestsStateShow.IsGetPersonRequestSuccess(requests))
                }
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    fun getHospitalRequests() {
        viewModelScope.launch {
            requestState.postValue(RequestsStateShow.Loading)
            try {
                val requests = repository.getHospitalRequests()
                if (requests.isEmpty()) {
                    requestState.postValue(RequestsStateShow.IsNotFound)
                } else {
                    allHospitalRequests = requests
                    filterRequestsByStatus(null) // null = All
                    requestState.postValue(RequestsStateShow.IsGetHospitalRequestSuccess(requests))
                }
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    fun filterRequestsByStatus(status: RequestStatus?) {
        val filteredHospital = if (status == null) {
            allHospitalRequests
        } else {
            allHospitalRequests.filter { it.status == status }
        }
        val filteredPerson = if (status == null) {
            allPersonRequests
        } else {
            allPersonRequests.filter { it.status == status }
        }

        when (status) {
            RequestStatus.Completed -> {
                initCompleteRequestsAdapter.postValue(filteredPerson)
            }

            RequestStatus.Canceled -> {
                initCanceledRequestsAdapter.postValue(filteredPerson)
            }

            RequestStatus.Pending -> {
                initPendingRequestsAdapter.postValue(filteredPerson)
            }

            RequestStatus.Confirmed -> {
                initConfirmedRequestsAdapter.postValue(filteredPerson)
            }

            else -> {}
        }

        if (filteredPerson.isEmpty() && filteredHospital.isEmpty()) {
            requestState.postValue(RequestsStateShow.IsNotFound)
        } else {
            if (filteredPerson.isNotEmpty())
            requestState.postValue(RequestsStateShow.IsPersonFilterSuccess(status, filteredPerson))
            if (filteredHospital.isNotEmpty())
            requestState.postValue(RequestsStateShow.IsHospitalFilterSuccess(status, filteredHospital))
        }
    }

    fun getCurrentRequest() {
        viewModelScope.launch {
            try {
                val current = repository.getCurrentPersonRequest()
                if (listOf(current).isEmpty()) {
                    requestState.postValue(RequestsStateShow.IsNotFound)
                    lastCurrentRequest = null
                } else if (current != lastCurrentRequest) {
                    lastCurrentRequest = current
                    requestState.postValue(
                        RequestsStateShow.IsUpdateSuccess(
                            listOf(
                                lastCurrentRequest!!
                            )
                        )
                    )

                }
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    fun confirmRequest(id: Int) {
        viewModelScope.launch {
            requestState.postValue(RequestsStateShow.Loading)
            try {
                val success = repository.confirmPersonRequest(id)
                requestState.postValue(RequestsStateShow.IsActionSuccess("Confirmed", success))
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    fun completeRequest(id: Int) {
        viewModelScope.launch {
            requestState.postValue(RequestsStateShow.Loading)
            try {
                val success = repository.completePersonRequest(id)
                requestState.postValue(RequestsStateShow.IsActionSuccess("Completed", success))
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    fun cancelRequest(id: Int) {
        viewModelScope.launch {
            requestState.postValue(RequestsStateShow.Loading)
            try {
                val success = repository.cancelPersonRequest(id)
                requestState.postValue(RequestsStateShow.IsActionSuccess("Canceled", success))
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }

    fun deleteRequest(id: Int) {
        viewModelScope.launch {
            requestState.postValue(RequestsStateShow.Loading)
            try {
                val success = repository.deletePersonRequest(id)
                requestState.postValue(RequestsStateShow.IsDeleteSuccess(success))
            } catch (e: Exception) {
                requestState.postValue(RequestsStateShow.ShowError(e.message ?: "Unknown error"))
            }
        }
    }
}

sealed class RequestsStateShow {
    data object Loading : RequestsStateShow()
    data object IsNotFound : RequestsStateShow()

    class IsGetPersonRequestSuccess(val requests: List<PersonNotificationResponse>) :
        RequestsStateShow()

    class IsGetHospitalRequestSuccess(val requests: List<HospitalNotificationResponse>) :
        RequestsStateShow()

    class IsPersonFilterSuccess(
        val status: RequestStatus?,
        val filtered: List<PersonNotificationResponse>
    ) : RequestsStateShow()

    class IsUpdateSuccess(val requests: List<PersonNotificationResponse>) : RequestsStateShow()
    class IsDeleteSuccess(val isSuccess: Boolean) : RequestsStateShow()
    class IsActionSuccess(val action: String, val isSuccess: Boolean) : RequestsStateShow()
    class ShowError(val errorMessage: String) : RequestsStateShow()
    class IsHospitalFilterSuccess(
        val status: RequestStatus?,
      val  filteredHospital: List<HospitalNotificationResponse>
    ) : RequestsStateShow()
}

