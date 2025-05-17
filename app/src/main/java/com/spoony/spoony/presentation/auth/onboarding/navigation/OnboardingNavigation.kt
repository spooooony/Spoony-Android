package com.spoony.spoony.presentation.auth.onboarding.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
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
    navigateToMap: () -> Unit
) {
    composable<Onboarding> {
        OnboardingRoute(
            navigateToMap = navigateToMap
        )
    }
}

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    navigateToMap: () -> Unit,
    viewModel: OnboardingViewModel
) {
    composable<StepOne>(
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        OnBoardingStepOneRoute(
            viewModel = viewModel,
            onNextButtonClick = {
                navController.navigate(
                    route = StepTwo,
                    navOptions = navOptions {
                        restoreState = true
                    }
                )
            }
        )
    }

    composable<StepTwo>(
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        OnboardingStepTwoRoute(
            viewModel = viewModel,
            onNextButtonClick = {
                navController.navigate(StepThree)
            }
        )
    }

    composable<StepThree>(
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        OnboardingStepThreeRoute(
            viewModel = viewModel,
            onNextButtonClick = {
                viewModel.updateCurrentStep(OnboardingSteps.END)
                navController.navigate(
                    route = End,
                    navOptions = navOptions {
                        popUpTo<StepOne> {
                            inclusive = true
                        }
                    }
                )
            }
        )
    }

    composable<End>(
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        OnboardingEndRoute(
            viewModel = viewModel,
            navigateToMap = navigateToMap
        )
    }
}

@Serializable
data object Onboarding : Route
