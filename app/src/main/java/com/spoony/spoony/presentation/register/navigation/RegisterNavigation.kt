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
import com.spoony.spoony.presentation.register.RegisterEndRoute
import com.spoony.spoony.presentation.register.RegisterRoute
import com.spoony.spoony.presentation.register.RegisterStartRoute
import com.spoony.spoony.presentation.register.RegisterSteps
import com.spoony.spoony.presentation.register.RegisterViewModel
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.register.navigation.RegisterRoute.End
import com.spoony.spoony.presentation.register.navigation.RegisterRoute.Start
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    registerType: RegisterType = RegisterType.CREATE,
    postId: Int? = null,
    navOptions: NavOptions? = null
) {
    navigate(Register(registerType, postId), navOptions)
}

fun NavGraphBuilder.registerNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToPostDetail: (postId: Int) -> Unit
) {
    composable<Register> {
        RegisterRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToExplore = navigateToExplore,
            navigateToPostDetail = navigateToPostDetail
        )
    }
}

fun NavGraphBuilder.registerGraph(
    navController: NavHostController,
    navigateToExplore: () -> Unit,
    navigateToPostDetail: (postId: Int) -> Unit,
    onUpdateProgress: (RegisterSteps) -> Unit,
    viewModel: RegisterViewModel,
    onResetRegisterState: () -> Unit
) {
    composable<Start>(
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
        RegisterStartRoute(
            viewModel = viewModel,
            onNextClick = {
                navController.navigate(End)
                onUpdateProgress(RegisterSteps.FINAL)
            },
            onInitialProgress = {
                onUpdateProgress(RegisterSteps.INIT)
            }
        )
    }

    composable<End>(
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
        RegisterEndRoute(
            viewModel = viewModel,
            onRegisterComplete = {
                onResetRegisterState()
                navController.popBackStack(Start, true)
                navigateToExplore()
            },
            onEditComplete = { postId ->
                onResetRegisterState()
                navigateToPostDetail(postId)
            }
        )
    }
}

@Serializable
data class Register(
    val registerType: RegisterType = RegisterType.CREATE,
    val postId: Int? = null
) : MainTabRoute
