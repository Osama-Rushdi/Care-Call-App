package com.example.carecallapp.data.repository.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.domain.model.hospital_content.BloodBag
import com.example.carecallapp.domain.use_cases.AddBloodUseCase
import com.example.carecallapp.domain.use_cases.DeleteBloodUseCase
import com.example.carecallapp.domain.use_cases.GetAllBloodBagUseCase
import com.example.carecallapp.domain.use_cases.UpdateBloodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyBloodBankViewModel @Inject constructor(
    private val getAllBloodBagUseCase: GetAllBloodBagUseCase,
//    private val getBloodByIdUseCase: GetBloodByIdUseCase,
    private val updateBloodUseCase: UpdateBloodUseCase,
    private val addBloodUseCase: AddBloodUseCase,
    private val deleteBloodUseCase: DeleteBloodUseCase

) : ViewModel() {
    val bloodStateShow = MutableLiveData<BloodStateShow>()
    val initBloodAdapter = MutableLiveData<List<BloodBag?>?>()

    fun getAllBloodBag() {
        viewModelScope.launch {
            bloodStateShow.postValue(BloodStateShow.Loading)
            try {
                val bloodBags = getAllBloodBagUseCase.execute()
                if (bloodBags.isEmpty())
                    bloodStateShow.postValue(BloodStateShow.IsFound)
                else {
                    Log.d("kkk", "accounts: $bloodBags")
                    bloodStateShow.postValue(BloodStateShow.IsSuccess(bloodBags))
                    initBloodAdapter.postValue(bloodBags)
                }
            } catch (e: Exception) {
                Log.e("kkk", "Error in showAccounts: ${e.message}", e)
                bloodStateShow.postValue(BloodStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun addBloodBag(blood: BloodBag) {
        viewModelScope.launch {
            bloodStateShow.postValue(BloodStateShow.Loading)
            try {
                val bloodBag = addBloodUseCase.execute(blood)
                Log.d("kkk", "addBloodUseCase: $bloodBag")
                bloodStateShow.postValue(BloodStateShow.IsAddSuccess(bloodBag))

            } catch (e: Exception) {
                Log.e("kkk", "Error in addBloodUseCase: ${e.message}", e)
                bloodStateShow.postValue(BloodStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun updateBloodBag(id: Int, blood: BloodBag) {
        viewModelScope.launch {
            try {
                val bloodBags = updateBloodUseCase.execute(id, blood)
                Log.d("kkk", "updateBloodUseCase: $bloodBags")
                bloodStateShow.postValue(BloodStateShow.IsUpdateSuccess(bloodBags))
            } catch (e: Exception) {
                Log.e("kkk", "Error in showAccounts: ${e.message}", e)
                bloodStateShow.postValue(BloodStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun deleteBloodBag(id: Int) {
        viewModelScope.launch {
            bloodStateShow.postValue(BloodStateShow.Loading)
            try {
                val bloodBags = deleteBloodUseCase.execute(id)
                Log.d("kkk", "deleteBloodUseCase: $bloodBags")
                bloodStateShow.postValue(BloodStateShow.IsDeleteSuccess(bloodBags))
                getAllBloodBag()
            } catch (e: Exception) {
                Log.e("kkk", "Error in deleteBloodUseCase: ${e.message} $id")
                bloodStateShow.postValue(BloodStateShow.ShowError(e.message.toString()))
            }
        }
    }
}

sealed class BloodStateShow {

    class IsAddSuccess(val blood: BloodBag) : BloodStateShow()
    class IsDeleteSuccess(val isSuccess: Boolean) : BloodStateShow()
    class IsUpdateSuccess(val isSuccess: BloodBag) : BloodStateShow()
    class IsSuccess(val bags: List<BloodBag>) : BloodStateShow()
    data class ShowError(val errorMessage: String) : BloodStateShow()
    data object IsFound : BloodStateShow()
    data object Loading : BloodStateShow()

}
