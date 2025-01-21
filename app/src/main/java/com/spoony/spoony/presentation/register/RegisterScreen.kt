package com.spoony.spoony.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.spoony.spoony.presentation.register.component.TopLinearProgressBar
import com.spoony.spoony.presentation.register.navigation.RegisterRoute
import com.spoony.spoony.presentation.register.navigation.registerGraph

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

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is RegisterSideEffect.ShowError -> {
                    showSnackBar(effect.message)
                }
                is RegisterSideEffect.ShowDuplicateError -> {
                    showSnackBar(effect.message)
                }
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(horizontal = 20.dp)
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp, bottom = 10.dp)
        ) {
            TopLinearProgressBar(
                currentStep = state.currentStep,
                totalSteps = 3f
            )
        }

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

        if (state.isLoading) { }
    }
}
