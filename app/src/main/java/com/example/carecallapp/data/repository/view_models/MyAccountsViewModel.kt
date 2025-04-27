package com.example.carecallapp.data.repository.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.data.api.ApiManager
import com.example.carecallapp.domain.model.hospital_accounts.PersonServiceResponse
import com.example.carecallapp.domain.use_cases.GetPeopleAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyAccountsViewModel @Inject constructor(
    private val getPeopleAccountsUseCase: GetPeopleAccountsUseCase
) : ViewModel() {
    val accountStateShow = MutableLiveData<AccountsStateShow>()
    val initAccountAdapter = MutableLiveData<List<PersonServiceResponse?>?>()

    fun showAccounts(type: String, hospitalId: String? = ApiManager.FAKE_HOSPITAL_ID) {
        if (hospitalId.isNullOrBlank()) {
            Log.e("kkk", "Invalid hospitalId")
            return
        }
        viewModelScope.launch {
            accountStateShow.postValue(AccountsStateShow.Loading)
            try {
                val accounts = getPeopleAccountsUseCase.execute(type, hospitalId)
                Log.d("kkk", "accounts: $accounts")
                accountStateShow.postValue(AccountsStateShow.IsSuccess(accounts))
                initAccountAdapter.postValue(accounts)

            } catch (e: Exception) {
                Log.e("kkk", "Error in showAccounts: ${e.message}", e)
                accountStateShow.postValue(AccountsStateShow.ShowError(e.message.toString()))
            }
        }
    }}
    sealed class AccountsStateShow {
        data object Loading : AccountsStateShow()
        class IsSuccess(val accounts: List<PersonServiceResponse>) : AccountsStateShow()
        data class ShowError(val errorMessage: String) : AccountsStateShow()
    }



