package com.example.carecallapp.domain.model.hospital_content

import java.io.Serializable


data class RoomAndNursery(val id :Int=0, val roomNumber: String, val status: Status, val type: RoomType)

enum class Status {
    Available,
    NotAvailable
}

enum class RoomType : Serializable {
    ICU,
    Nursery
}