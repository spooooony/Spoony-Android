package com.spoony.spoony.presentation.main

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.spoony.spoony.core.designsystem.component.snackbar.TextSnackbar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.presentation.attendance.navigation.attendanceNavGraph
import com.spoony.spoony.presentation.auth.onboarding.navigation.onboardingNavGraph
import com.spoony.spoony.presentation.auth.signin.navigation.signInNavGraph
import com.spoony.spoony.presentation.auth.termsofservice.navigation.termsOfServiceNavGraph
import com.spoony.spoony.presentation.explore.navigation.exploreNavGraph
import com.spoony.spoony.presentation.exploreSearch.navigation.exploreSearchNavGraph
import com.spoony.spoony.presentation.follow.navigation.followNavGraph
import com.spoony.spoony.presentation.gourmet.map.navigaion.mapNavGraph
import com.spoony.spoony.presentation.gourmet.search.navigation.mapSearchNavGraph
import com.spoony.spoony.presentation.main.component.MainBottomBar
import com.spoony.spoony.presentation.placeDetail.navigation.placeDetailNavGraph
import com.spoony.spoony.presentation.profileedit.navigation.profileEditGraph
import com.spoony.spoony.presentation.register.navigation.Register
import com.spoony.spoony.presentation.register.navigation.registerNavGraph
import com.spoony.spoony.presentation.report.navigation.reportNavGraph
import com.spoony.spoony.presentation.setting.navigation.settingPageNavGraph
import com.spoony.spoony.presentation.splash.navigation.splashNavGraph
import com.spoony.spoony.presentation.userpage.mypage.navigation.myPageNavGraph
import com.spoony.spoony.presentation.userpage.otherpage.navigation.otherPageNavGraph
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val SHOW_SNACKBAR_TIMEMILLIS = 3000L

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
                    onTabSelected = navigator::navigate
                )
            },
            modifier = Modifier
                .fillMaxSize()
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
                splashNavGraph(
                    navigateToMap = navigator::navigateToMap,
                    navigateToSignIn = navigator::navigateToSignIn,
                    paddingValues = paddingValues
                )

                signInNavGraph(
                    paddingValues = paddingValues,
                    navigateToMap = navigator::navigateToMap,
                    navigateToTermsOfService = navigator::navigateToTermsOfService
                )

                termsOfServiceNavGraph(
                    paddingValues = paddingValues,
                    navigateToOnboarding = navigator::navigateToOnboarding
                )

                onboardingNavGraph(
                    navigateToMap = navigator::navigateToMap
                )

                mapNavGraph(
                    paddingValues = paddingValues,
                    navigateToPlaceDetail = {
                        navigator.navigateToPlaceDetail(
                            postId = it
                        )
                    },
                    navigateToMapSearch = navigator::navigateToMapSearch,
                    navigateToExplore = navigator::navigateToExplore,
                    navigateToAttendance = navigator::navigateToAttendance,
                    navigateUp = navigator::navigateUp
                )

                exploreNavGraph(
                    paddingValues = paddingValues,
                    navigateToPlaceDetail = navigator::navigateToPlaceDetail,
                    navigateToRegister = navigator::navigateToRegister,
                    navigateToExploreSearch = navigator::navigateToExploreSearch,
                    navigateToEditReview = navigator::navigateToReviewEdit,
                    navigateToReport = { reportTargetId, type ->
                        navigator.navigateToReport(
                            reportTargetId = reportTargetId,
                            type = type
                        )
                    }
                )

                exploreSearchNavGraph(
                    paddingValues = paddingValues,
                    navigateToUserProfile = navigator::navigateToOtherPage,
                    navigateToReport = { reportTargetId, type ->
                        navigator.navigateToReport(
                            reportTargetId = reportTargetId,
                            type = type
                        )
                    },
                    navigateToPlaceDetail = navigator::navigateToPlaceDetail,
                    navigateUp = navigator::navigateUp,
                    navigateToEditReview = navigator::navigateToReviewEdit
                )

                registerNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToExplore = navigator::navigateToExplore,
                    navigateToPostDetail = {
                        navigator.navigateToPlaceDetail(
                            postId = it,
                            navOptions = navOptions {
                                popUpTo<Register> {
                                    inclusive = true
                                }
                            }
                        )
                    }
                )

                myPageNavGraph(
                    paddingValues = paddingValues,
                    navigateToSettings = navigator::navigateToSettingPage,
                    navigateToFollow = navigator::navigateToFollow,
                    navigateToProfileEdit = navigator::navigateToProfileEdit,
                    navigateToRegister = navigator::navigateToRegister,
                    navigateToReviewDetail = navigator::navigateToPlaceDetail,
                    navigateToEditReview = navigator::navigateToReviewEdit,
                    navigateToAttendance = navigator::navigateToAttendance
                )

                profileEditGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp
                )

                otherPageNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToFollow = navigator::navigateToFollow,
                    navigateToReviewDetail = navigator::navigateToPlaceDetail,
                    navigateToReviewReport = navigator::navigateToReport,
                    navigateToUserReport = navigator::navigateToReport
                )

                followNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToUserProfile = navigator::navigateToOtherPage
                )

                placeDetailNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToReport = { reportTargetId, type ->
                        navigator.navigateToReport(
                            reportTargetId = reportTargetId,
                            type = type
                        )
                    },
                    navigateToUserProfile = navigator::navigateToOtherPage,
                    navigateToEditReview = navigator::navigateToReviewEdit,
                    navigateToAttendance = navigator::navigateToAttendance
                )

                reportNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToExplore = navigator::navigateToExplore
                )

                mapSearchNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp,
                    navigateToLocationMap = { locationId, locationName, scale, latitude, longitude ->
                        navigator.navigateToLocationMap(
                            locationId = locationId,
                            locationName = locationName,
                            scale = scale,
                            latitude = latitude,
                            longitude = longitude
                        )
                    }
                )

                settingPageNavGraph(
                    navigateUp = navigator::navigateUp
                )

                attendanceNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = navigator::navigateUp
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
