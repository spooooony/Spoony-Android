package com.spoony.spoony.presentation.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.presentation.register.RegisterScreen
import com.spoony.spoony.presentation.register.RegisterStepOneScreen
import com.spoony.spoony.presentation.register.RegisterStepTwoScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null
) {
    navigate(Register, navOptions)
}

fun NavGraphBuilder.registerNavGraph(
    paddingValues: PaddingValues
) {
    composable<Register> {
        RegisterScreen(paddingValues = paddingValues)
    }
}

fun NavGraphBuilder.registerGraph(
    navController: NavHostController,
    onUpdateProgress: (Float) -> Unit
) {
    composable<RegisterRoute.StepOne> {
        RegisterStepOneScreen(
            onNextClick = {
                navController.navigate(RegisterRoute.StepTwo)  // popUpTo 제거
                onUpdateProgress(2f)
            }
        )
    }

    composable<RegisterRoute.StepTwo> {
        RegisterStepTwoScreen(
            onComplete = {
                onUpdateProgress(3f)
            }
        )
    }
}

@Serializable
data object Register : MainTabRoute
