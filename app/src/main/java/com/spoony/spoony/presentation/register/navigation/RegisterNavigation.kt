package com.spoony.spoony.presentation.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.map.navigaion.Map
import kotlinx.serialization.Serializable

fun NavController.navigateRegister(
    navOptions: NavOptions? = null
) {
    navigate(Register, navOptions)
}

fun NavGraphBuilder.RegisterNavGraph(
    paddingValues: PaddingValues
) {
    composable<Register> {
    }
}


@Serializable
data object Register : MainTabRoute
