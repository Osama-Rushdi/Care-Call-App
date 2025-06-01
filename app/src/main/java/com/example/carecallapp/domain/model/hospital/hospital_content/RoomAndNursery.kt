package com.example.carecallapp.domain.model.hospital.hospital_content

import java.io.Serializable


data class RoomAndNursery(val id :Int=0, val roomNumber: String, val status: RoomStatus, val type: RoomType)

enum class RoomStatus {
    Available, Occupied, Reserved, UnderMaintenance
}

enum class RoomType : Serializable {
    ICU,
    Nursery
}