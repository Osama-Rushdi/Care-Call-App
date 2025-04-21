package com.example.carecallapp.data.repository.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.data.api.ApiManager
import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.hospital_accounts.PersonService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyAccountsViewModel @Inject constructor(
    private val dataSource: RemoteDataSource
) : ViewModel() {
    val stateShow = MutableLiveData<StateShow>()
    val initAdapter = MutableLiveData<List<PersonService?>?>()

    fun showAccounts(type: String, hospitalId: String? = ApiManager.FAKE_HOSPITAL_ID) {
        if (hospitalId.isNullOrBlank()) {
            Log.e("kkk", "Invalid hospitalId")
            return
        }

        viewModelScope.launch {
            stateShow.postValue(StateShow.Loading)
            try {
                val accounts = dataSource.getPeopleAccounts(type, hospitalId)
                Log.d("kkk", "accounts: $accounts")
                stateShow.postValue(StateShow.IsSuccess(accounts))
                initAdapter.postValue(accounts)

            } catch (e: Exception) {
                Log.e("kkk", "Error in showAccounts: ${e.message}", e)
                stateShow.postValue(StateShow.ShowError(e.message.toString()))
            }
        }
    }
}

sealed class StateShow {
    data object Loading : StateShow()
    class IsSuccess(val accounts: List<PersonService>) : StateShow()
    data class ShowError(val errorMessage: String) : StateShow()
}
