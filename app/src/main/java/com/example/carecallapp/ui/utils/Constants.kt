package com.example.carecallapp.ui.utils

import android.content.Context
import com.example.carecallapp.R
import dagger.hilt.android.qualifiers.ApplicationContext


object Constants {
    const val IS_FIRST_KEY="IS_FIRST_KEY"
    const val IS_FIRST_NAME="ISFIRSTNAME"
    const val SHARED_FEATURE_KEY = "SHARED_FEATURE_KEY"
    const val SHARED_FEATURE_NAME = "SHARED_FEATURE_NAME"
    const val OSRM_BASE_URL = "https://router.project-osrm.org/"
    const val USER_ROLE_KEY = "User role"
    const val USER_ID_KEY = "hospitalIdKey"
    const val SHARED_TOKEN_KEY = "token Key"
    const val SHARED_TOKEN_NAME = "shared Pre"
    fun getMapPersonOfTabs(context: Context): Map<String, Int> {
        return mapOf(
            context.getString(R.string.current_requests) to 0,
            context.getString(R.string.completed_requests) to 1,
            context.getString(R.string.cancelled_requests) to 2,
            context.getString(R.string.pending_requests) to 3,
            context.getString(R.string.confirmed_requests) to 4
        )
    }
fun getMapHospitalOfTabs(context: Context): Map<String, Int> {
    return mapOf(
        context.getString(R.string.all_requests) to 0,
        context.getString(R.string.completed_requests) to 1,
        context.getString(R.string.cancelled_requests) to 2,
        context.getString(R.string.pending_requests) to 3,
        context.getString(R.string.confirmed_requests) to 4
    )
}
}
