package com.example.carecallapp.data.repository.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carecallapp.domain.model.hospital_content.RoomAndNursery
import com.example.carecallapp.domain.model.hospital_content.RoomType
import com.example.carecallapp.domain.use_cases.AddRoomAndNurseryUseCase
import com.example.carecallapp.domain.use_cases.DeleteRoomOrNurseryUseCase
import com.example.carecallapp.domain.use_cases.GetAllRoomsAndNurseriesUseCase
import com.example.carecallapp.domain.use_cases.GetRoomAndNurseryByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyRoomAndNurseryViewModel @Inject constructor(
    private val getAllEmergencyRoomUseCase: GetAllRoomsAndNurseriesUseCase,
    private val addRoomUseCase: AddRoomAndNurseryUseCase,
    private val getRoomAndNurseryByIdUseCase: GetRoomAndNurseryByIdUseCase,
    private val deleteRoomUseCase: DeleteRoomOrNurseryUseCase

) : ViewModel() {
    val roomStateShow = MutableLiveData<RoomStateShow>()
    val initRoomAdapter = MutableLiveData<List<RoomAndNursery?>?>()

    fun getAllRooms() {
        viewModelScope.launch {
            roomStateShow.postValue(RoomStateShow.Loading)
            try {
                val emergencyRooms = getAllEmergencyRoomUseCase.execute()
                val room = emergencyRooms.filter {
                    Log.d("kkk", "it.type.name:${it.type.name} ")
                    Log.d("kkk", "RoomType.ICU.name:${RoomType.ICU.name} ")
                    Log.d(
                        "kkk",
                        "(it.type.name == RoomType.ICU.name):${(it.type.name == RoomType.ICU.name)} "
                    )
                    (it.type.name == RoomType.ICU.name)
                }
                if (room.isEmpty()) {
                    roomStateShow.postValue(RoomStateShow.IsFound)
                    Log.d("kkk", "emergencyRooms: $emergencyRooms")
                    Log.d("kkk", "room: $room")

                } else {
                    Log.d("kkk", "emergencyRooms: $emergencyRooms")
                    Log.d("kkk", "room: $room")
                    roomStateShow.postValue(RoomStateShow.IsSuccess(emergencyRooms))
                    initRoomAdapter.postValue(emergencyRooms)
                }
            } catch (e: Exception) {
                Log.e("kkk", "Error in getAllRooms: ${e.message}", e)
                roomStateShow.postValue(RoomStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun getAllNurseries() {
        viewModelScope.launch {
            roomStateShow.postValue(RoomStateShow.Loading)
            try {
                val rooms = getAllEmergencyRoomUseCase.execute()
                val nursery = rooms.filter {
                    Log.d("kkk", "it.type.name: ${it.type.name}")
                    (it.type.name == RoomType.Nursery.name)
                }
                Log.d("kkk", "nursery: ${nursery}")
                Log.d("kkk", "rooms: ${rooms}")
                if (nursery.isEmpty())
                    roomStateShow.postValue(RoomStateShow.IsFound)
                else {
                    Log.d("kkk", "accounts: $nursery")
                    roomStateShow.postValue(RoomStateShow.IsSuccess(nursery))
                    initRoomAdapter.postValue(nursery)
                }
            } catch (e: Exception) {
                Log.e("kkk", "Error in showAccounts: ${e.message}", e)
                roomStateShow.postValue(RoomStateShow.ShowError(e.message.toString()))
            }
        }
    }

//    fun getRoomAndNurseryById(id: Int) {
//        viewModelScope.launch {
//            roomStateShow.postValue(RoomStateShow.Loading)
//            try {
//                val bed = getRoomAndNurseryByIdUseCase.execute(id)
//
//            } catch (e: Exception) {
//                roomStateShow.postValue(RoomStateShow.ShowError(e.message.toString()))
//            }
//        }
//    }

    fun addRoomAndNursery(room: RoomAndNursery) {
        viewModelScope.launch {
            roomStateShow.postValue(RoomStateShow.Loading)
            try {
                val emergencyRoom = addRoomUseCase.execute(room)
                Log.d("kkk", "addRoomUseCase: $emergencyRoom")
                roomStateShow.postValue(RoomStateShow.IsAddSuccess(emergencyRoom))

            } catch (e: Exception) {
                Log.e("kkk", "Error in addRoomUseCase: ${e.message}", e)
                roomStateShow.postValue(RoomStateShow.ShowError(e.message.toString()))
            }
        }
    }

    fun deleteRoomOrNursery(id: Int) {
        viewModelScope.launch {
            roomStateShow.postValue(RoomStateShow.Loading)
            try {
                val deletedRoom = deleteRoomUseCase.execute(id)
                Log.d("kkk", "deleteRoomUseCase: $deletedRoom")
                roomStateShow.postValue(RoomStateShow.IsDeleteSuccess(true))

            } catch (e: Exception) {
                Log.e("kkk", "Error $id: in deleteRoomUseCase: ${e.message}")
                roomStateShow.postValue(RoomStateShow.ShowError(e.message.toString()))
            }
        }
    }
}

sealed class RoomStateShow {
    data object Loading : RoomStateShow()
    class IsSuccess(val rooms: List<RoomAndNursery>) : RoomStateShow()
    class IsAddSuccess(val room: RoomAndNursery) : RoomStateShow()
    class IsDeleteSuccess(val isSuccess: Boolean) : RoomStateShow()
    data class ShowError(val errorMessage: String) : RoomStateShow()
    data object IsFound : RoomStateShow()

}
