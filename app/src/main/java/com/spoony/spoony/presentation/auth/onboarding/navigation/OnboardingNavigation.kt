package com.spoony.spoony.presentation.auth.onboarding.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.auth.onboarding.OnBoardingStepOneRoute
import com.spoony.spoony.presentation.auth.onboarding.OnboardingEndRoute
import com.spoony.spoony.presentation.auth.onboarding.OnboardingRoute
import com.spoony.spoony.presentation.auth.onboarding.OnboardingStepThreeRoute
import com.spoony.spoony.presentation.auth.onboarding.OnboardingStepTwoRoute
import com.spoony.spoony.presentation.auth.onboarding.OnboardingSteps
import com.spoony.spoony.presentation.auth.onboarding.OnboardingViewModel
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute.End
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute.StepOne
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute.StepThree
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute.StepTwo
import kotlinx.serialization.Serializable

fun NavController.navigateToOnboarding(
    navOptions: NavOptions? = null
) {
    navigate(Onboarding, navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit
) {
    composable<Onboarding> {
        OnboardingRoute(
            paddingValues = paddingValues,
            navigateToMap = navigateToMap
        )
    }
}

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    navigateToMap: () -> Unit,
    onUpdateSteps: (OnboardingSteps) -> Unit,
    viewModel: OnboardingViewModel
) {
    composable<StepOne>(
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Right,
//                animationSpec = tween(500)
//            )
//        },
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentTransitionScope.SlideDirection.Left,
//                animationSpec = tween(500)
//            )
//        }
    ) {
        OnBoardingStepOneRoute(
            viewModel = viewModel,
            onNextButtonClick = {
                onUpdateSteps(OnboardingSteps.TWO)
                navController.navigate(StepTwo)
            }
        )
    }

    composable<StepTwo>(
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Right,
//                animationSpec = tween(500)
//            )
//        },
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentTransitionScope.SlideDirection.Left,
//                animationSpec = tween(500)
//            )
//        }
    ) {
        OnboardingStepTwoRoute(
            viewModel = viewModel,
            onNextButtonClick = {
                onUpdateSteps(OnboardingSteps.THREE)
                navController.navigate(StepThree)
            }
        )
    }

    composable<StepThree>(
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Right,
//                animationSpec = tween(500)
//            )
//        },
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentTransitionScope.SlideDirection.Left,
//                animationSpec = tween(500)
//            )
//        }
    ) {
        OnboardingStepThreeRoute(
            viewModel = viewModel,
            onNextButtonClick = {
                onUpdateSteps(OnboardingSteps.END)
                navController.navigate(End)
            }
        )
    }

    composable<End>(
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Left,
//                animationSpec = tween(500)
//            )
//        },
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentTransitionScope.SlideDirection.Right,
//                animationSpec = tween(500)
//            )
//        }
    ) {
        OnboardingEndRoute(
            viewModel = viewModel,
            navigateToMap = navigateToMap
        )
    }
}

@Serializable
data object Onboarding : Route
