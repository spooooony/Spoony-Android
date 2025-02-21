package com.spoony.spoony.presentation.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
import com.spoony.spoony.presentation.register.RegisterSteps
import com.spoony.spoony.presentation.register.RegisterViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null
) {
    navigate(Register, navOptions)
}

fun NavGraphBuilder.registerNavGraph(
    paddingValues: PaddingValues,
    navigateToExplore: () -> Unit
) {
    composable<Register> {
        RegisterScreen(
            paddingValues = paddingValues,
            navigateToExplore = navigateToExplore
        )
    }
}

fun NavGraphBuilder.registerGraph(
    navController: NavHostController,
    navigateToExplore: () -> Unit,
    onUpdateProgress: (RegisterSteps) -> Unit,
    viewModel: RegisterViewModel,
    onResetRegisterState: () -> Unit
) {
    composable<RegisterRoute.StepOne>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        }
    ) {
        RegisterStepOneScreen(
            viewModel = viewModel,
            onNextClick = {
                navController.navigate(RegisterRoute.StepTwo)
                onUpdateProgress(RegisterSteps.FIRST)
            },
            onInitialProgress = {
                onUpdateProgress(RegisterSteps.INIT)
            }
        )
    }

    composable<RegisterRoute.StepTwo>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        RegisterStepTwoScreen(
            viewModel = viewModel,
            onStepTwoComplete = {
                onUpdateProgress(RegisterSteps.FINISH)
            },
            onRegisterComplete = {
                onResetRegisterState()
                navController.popBackStack(RegisterRoute.StepOne, true)
                navigateToExplore()
            }
        )
    }
}

@Serializable
data object Register : MainTabRoute
