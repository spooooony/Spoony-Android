package com.spoony.spoony.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.register.component.RegisterTooltip
import com.spoony.spoony.presentation.register.component.TopLinearProgressBar
import com.spoony.spoony.presentation.register.navigation.RegisterRoute
import com.spoony.spoony.presentation.register.navigation.registerGraph
import kotlinx.coroutines.delay

const val SHOW_REGISTER_SNACKBAR_TIME = 3000L

@Composable
fun RegisterScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToExplore: () -> Unit,
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
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(SpoonyAndroidTheme.colors.white)
                .padding(horizontal = 20.dp)
        ) {
            TopLinearProgressBar(
                currentStep = state.currentStep,
                totalSteps = 3f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp, bottom = 10.dp)
            )

            NavHost(
                navController = navController,
                startDestination = RegisterRoute.StepOne,
                modifier = Modifier.weight(1f)
            ) {
                registerGraph(
                    navController = navController,
                    navigateToExplore = navigateToExplore,
                    onUpdateProgress = viewModel::updateStep,
                    onResetRegisterState = viewModel::resetState,
                    viewModel = viewModel
                )
            }

            if (state.isLoading) {
            }
        }

        AnimatedVisibility(
            visible = state.showRegisterSnackBar,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            RegisterTooltip(
                text = "장소를 등록하면 수저를 획득할 수 있어요"
            )
            LaunchedEffect(Unit) {
                delay(SHOW_REGISTER_SNACKBAR_TIME)
                viewModel.hideRegisterSnackBar()
            }
        }
    }
}
