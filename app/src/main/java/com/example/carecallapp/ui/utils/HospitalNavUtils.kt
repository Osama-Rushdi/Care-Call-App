package com.example.carecallapp.ui.utils


    import androidx.navigation.NavController
    import com.example.carecallapp.MobileNavigationDirections
    import com.example.carecallapp.domain.model.hospital.hospital_content.RoomType
    import com.example.carecallapp.domain.model.hospital.hospital_content.ServiceType


fun navigateToService(type: ServiceType, navController: NavController) {
        when (type) {
            ServiceType.Nursery -> {
                val action =MobileNavigationDirections.actionGlobalToRoomFragment(RoomType.Nursery)
                navController.navigate(action)
            }

            ServiceType.BloodBank -> {
                val action =MobileNavigationDirections.actionGlobalToBloodBankFragment()
                navController.navigate(action)
            }

            ServiceType.ICU -> {
                val action =MobileNavigationDirections.actionGlobalToRoomFragment(RoomType.ICU)
                navController.navigate(action)
            }

            else -> {}
        }
    }

