package com.spoony.spoony.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.register.component.TopLinearProgressBar
import com.spoony.spoony.presentation.register.model.RegisterState
import com.spoony.spoony.presentation.register.navigation.RegisterRoute
import com.spoony.spoony.presentation.register.navigation.registerGraph

@Composable
fun RegisterRoute(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToPostDetail: (postId: Int) -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is RegisterSideEffect.ShowSnackbar -> {
                    showSnackBar(effect.message)
                }

                is RegisterSideEffect.ShowError -> {
                    showSnackBar(effect.errorType.description)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadState()
    }

    RegisterScreen(
        state = state,
        navController = navController,
        onBackButtonClick = navigateUp,
        navigateToExplore = navigateToExplore,
        navigateToPostDetail = navigateToPostDetail,
        onUpdateProgress = viewModel::updateStep,
        onResetRegisterState = viewModel::resetState,
        viewModel = viewModel,
        modifier = modifier
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    navController: NavHostController,
    onBackButtonClick: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToPostDetail: (postId: Int) -> Unit,
    onUpdateProgress: (RegisterSteps) -> Unit,
    onResetRegisterState: () -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(SpoonyAndroidTheme.colors.white)
        ) {
            TitleTopAppBar(
                onBackButtonClick = {
                    if (state.currentStep == RegisterSteps.FINAL.step) navController.navigateUp() else onBackButtonClick()
                }
            )

            TopLinearProgressBar(
                currentStep = state.currentStep,
                totalSteps = 2f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            )

            NavHost(
                navController = navController,
                startDestination = RegisterRoute.Start,
                modifier = Modifier.weight(1f)
            ) {
                registerGraph(
                    navController = navController,
                    navigateToExplore = navigateToExplore,
                    navigateToPostDetail = navigateToPostDetail,
                    onUpdateProgress = onUpdateProgress,
                    onResetRegisterState = onResetRegisterState,
                    viewModel = viewModel
                )
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable(onClick = { })
                    .background(SpoonyAndroidTheme.colors.black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
