package com.spoony.spoony.presentation.attendance.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.attendance.AttendanceRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToAttendance(
    navOptions: NavOptions? = null
) {
    navigate(
        Attendance,
        navOptions
    )
}

fun NavGraphBuilder.attendanceNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    composable<Attendance> {
        AttendanceRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data object Attendance : Route
