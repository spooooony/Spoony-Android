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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spoony.spoony.presentation.register.component.TopLinearProgressBar
import com.spoony.spoony.presentation.register.navigation.RegisterRoute
import com.spoony.spoony.presentation.register.navigation.registerGraph


@Composable
fun RegisterScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    var currentProgress by remember { mutableFloatStateOf(0f) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        when (currentBackStackEntry?.destination?.route) {
            RegisterRoute.StepOne::class.qualifiedName -> currentProgress = 1f
            RegisterRoute.StepTwo::class.qualifiedName -> currentProgress = 2f
            else -> currentProgress = 0f
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp, bottom = 10.dp)
        ) {
            TopLinearProgressBar(
                currentStep = currentProgress,
                totalSteps = 3f,
                modifier = Modifier
            )
        }

        NavHost(
            navController = navController,
            startDestination = RegisterRoute.StepOne,
            modifier = Modifier.weight(1f)
        ) {
            registerGraph(
                navController = navController,
                onUpdateProgress = { progress ->
                    currentProgress = progress
                }
            )
        }
    }
}
