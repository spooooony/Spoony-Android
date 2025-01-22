package com.spoony.spoony.presentation.main

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.spoony.spoony.core.designsystem.component.snackbar.TextSnackbar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.presentation.explore.navigation.exploreNavGraph
import com.spoony.spoony.presentation.main.component.MainBottomBar
import com.spoony.spoony.presentation.map.navigaion.mapNavGraph
import com.spoony.spoony.presentation.map.search.navigation.mapSearchNavGraph
import com.spoony.spoony.presentation.map.search.navigation.navigateToMapSearch
import com.spoony.spoony.presentation.placeDetail.navigation.navigateToPlaceDetail
import com.spoony.spoony.presentation.placeDetail.navigation.placeDetailNavGraph
import com.spoony.spoony.presentation.register.navigation.registerNavGraph
import com.spoony.spoony.presentation.report.navigation.reportNavGraph
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val SHOW_SNACKBAR_TIMEMILLIS = 2500L

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            val job = launch {
                snackBarHostState.showSnackbar(message)
            }
            delay(SHOW_SNACKBAR_TIMEMILLIS)
            job.cancel()
        }
    }

    SpoonyBackHandler(
        context = context,
        onShowSnackbar = {
            onShowSnackBar("한번 더 누르면 앱이 종료돼요!")
        }
    )

    CompositionLocalProvider(
        LocalSnackBarTrigger provides onShowSnackBar
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                    TextSnackbar(text = snackbarData.visuals.message)
                }
            },
            bottomBar = {
                MainBottomBar(
                    visible = navigator.shouldShowBottomBar(),
                    tabs = MainTab.entries.toPersistentList(),
                    currentTab = navigator.currentTab,
                    onTabSelected = navigator::navigate
                )
            }
        ) { paddingValues ->
            NavHost(
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                },
                navController = navigator.navController,
                startDestination = navigator.startDestination
            ) {
                mapNavGraph(
                    paddingValues = paddingValues,
                    navigateToPlaceDetail = { navigator.navController.navigateToPlaceDetail(it, 1) },
                    navigateToMapSearch = navigator.navController::navigateToMapSearch,
                    navigateUp = navigator.navController::navigateUp
                )

                exploreNavGraph(
                    paddingValues = paddingValues,
                    navHostController = navigator.navController
                )

                registerNavGraph(
                    paddingValues = paddingValues,
                    navigateToExplore = navigator::navigateRegisterToExplore
                )

                placeDetailNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToReport = navigator::navigateToReport
                )

                reportNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToExplore = navigator::navigateToExplore
                )

                mapSearchNavGraph(
                    paddingValues = paddingValues
                )
            }
        }
    }
}

@Composable
fun SpoonyBackHandler(
    context: Context,
    enabled: Boolean = true,
    exitDelayMillis: Long = 3000L,
    onShowSnackbar: () -> Unit = {}
) {
    var backPressedTime by remember {
        mutableLongStateOf(0L)
    }

    BackHandler(enabled = enabled) {
        if (System.currentTimeMillis() - backPressedTime <= exitDelayMillis) {
            (context as Activity).finish()
        } else {
            onShowSnackbar()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
