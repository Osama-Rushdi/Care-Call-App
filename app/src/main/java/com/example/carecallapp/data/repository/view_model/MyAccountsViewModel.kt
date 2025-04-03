package com.example.carecallapp.data.repository.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.domain.model.hospital_accounts.Accounts
import com.example.carecallapp.domain.repository.HospitalRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyAccountsViewModel @Inject constructor(
    private val repository: HospitalRepository
) : ViewModel() {
     val stateShow = MutableLiveData<StateShow>()
     val initAdapter = MutableLiveData<List<Accounts>>()

    fun showAccounts(userId: String?, token: String? = null) {
        if (userId.isNullOrBlank()) {
            Log.e("kkk", "Invalid sourceId")
            return
        }
        viewModelScope.launch {
            stateShow.value = StateShow.Loading
            try {
                val accounts =repository.getAccounts(userId,token)
                Log.d("kkk", "showAccounts: $accounts")
                if (accounts.isNotEmpty())
                    stateShow.value=StateShow.IsSuccess(accounts)
                initAdapter.value = accounts
            } catch (e: Exception) {
                stateShow.value = StateShow.ShowError
                Log.e("kkk", "Error in showAccounts: ${e.message}", e)
            }
        }
    }
}

sealed class StateShow {
    data object Loading : StateShow()
    class IsSuccess(val accounts: List<Accounts>) : StateShow()
    data object ShowError : StateShow()
    data class selectDoctor(val isDoctor: Boolean) : StateShow()

}