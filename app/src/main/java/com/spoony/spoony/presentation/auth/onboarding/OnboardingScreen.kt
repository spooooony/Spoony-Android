package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.auth.onboarding.navigation.OnboardingRoute
import com.spoony.spoony.presentation.auth.onboarding.navigation.onboardingGraph
import com.spoony.spoony.presentation.register.component.TopLinearProgressBar

@Composable
fun OnboardingRoute(
    paddingValues: PaddingValues,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(SpoonyAndroidTheme.colors.white)
    ) {
        TitleTopAppBar(
            onBackButtonClick = { }
        )

        TopLinearProgressBar(
            currentStep = state.currentStep,
            totalSteps = 3f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp)
        )

        NavHost(
            navController = navController,
            startDestination = OnboardingRoute.StepOne,
            modifier = Modifier
                .weight(1f)
        ) {
            onboardingGraph(
                navController = navController,
                onUpdateSteps = viewModel::updateCurrentStep,
                viewModel = viewModel,
                navigateToMap = navigateToMap
            )
        }
    }
}
