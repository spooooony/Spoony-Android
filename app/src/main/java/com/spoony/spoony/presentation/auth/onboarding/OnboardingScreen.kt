package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.spoony.spoony.core.designsystem.component.topappbar.SpoonyBasicTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.auth.onboarding.component.OnboardingTopAppBar
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute.End
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute.StepOne
import com.spoony.spoony.presentation.auth.onboarding.navigation.onboardingGraph
import com.spoony.spoony.presentation.register.component.TopLinearProgressBar

@Composable
fun OnboardingRoute(
    navigateToMap: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingScreen(
        viewModel = viewModel,
        navigateToMap = navigateToMap
    )
}

@Composable
private fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    navigateToMap: () -> Unit
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showSnackbar = LocalSnackBarTrigger.current

    when (state.signUpState) {
        is UiState.Empty -> {
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

        is UiState.Failure -> showSnackbar((state.signUpState as? UiState.Failure)?.msg.orEmpty())
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(SpoonyAndroidTheme.colors.white)
    ) {
        when (state.currentStep) {
            OnboardingSteps.TWO -> {
                OnboardingTopAppBar(
                    onBackButtonClick = navController::navigateUp,
                    onSkipButtonClick = {
                        viewModel.skipStep()
                        navController.navigate(OnboardingRoute.StepThree)
                    }
                )
            }

            OnboardingSteps.THREE -> {
                OnboardingTopAppBar(
                    onBackButtonClick = navController::navigateUp,
                    onSkipButtonClick = {
                        viewModel.skipStep()
                        viewModel.signUp()
                    }
                )
            }

            else -> {
                SpoonyBasicTopAppBar(
                    modifier = Modifier
                        .height(56.dp)
                        .statusBarsPadding()
                )
            }
        }

        if (state.currentStep != OnboardingSteps.END) {
            TopLinearProgressBar(
                currentStep = state.currentStep.step,
                totalSteps = 3f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            )
        }

        NavHost(
            navController = navController,
            startDestination = StepOne,
            modifier = Modifier
                .weight(1f)
        ) {
            onboardingGraph(
                navController = navController,
                viewModel = viewModel,
                navigateToMap = navigateToMap
            )
        }
    }
}
