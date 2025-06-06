package com.spoony.spoony.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.spoony.spoony.presentation.attendance.navigation.navigateToAttendance
import com.spoony.spoony.presentation.auth.onboarding.navigation.navigateToOnboarding
import com.spoony.spoony.presentation.auth.signin.navigation.navigateToSignIn
import com.spoony.spoony.presentation.auth.termsofservice.navigation.navigateToTermsOfService
import com.spoony.spoony.presentation.explore.navigation.navigateToExplore
import com.spoony.spoony.presentation.exploreSearch.navigation.navigateToExploreSearch
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.navigation.navigateToFollow
import com.spoony.spoony.presentation.gourmet.map.navigaion.Map
import com.spoony.spoony.presentation.gourmet.map.navigaion.navigateToMap
import com.spoony.spoony.presentation.gourmet.search.navigation.navigateToMapSearch
import com.spoony.spoony.presentation.placeDetail.navigation.navigateToPlaceDetail
import com.spoony.spoony.presentation.profileedit.navigation.navigateToProfileEdit
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.register.navigation.navigateToRegister
import com.spoony.spoony.presentation.report.ReportType
import com.spoony.spoony.presentation.report.navigation.navigateToReport
import com.spoony.spoony.presentation.setting.navigation.navigateToSettingPage
import com.spoony.spoony.presentation.splash.navigation.Splash
import com.spoony.spoony.presentation.userpage.mypage.navigation.navigateToMyPage
import com.spoony.spoony.presentation.userpage.otherpage.navigation.navigateToOtherPage
import timber.log.Timber

const val NAVIGATION_ROOT = 0

private val bottomNavScreens = listOf(
    "Map",
    "Explore",
    "ExploreSearch",
    "Follow",
    "MapSearch",
    "MyPage",
    "OtherPage"
)

class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = Splash

    private var lastMainTab: MainTab = MainTab.MAP

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val screenName = currentDestination?.route
            ?.substringAfterLast(".")
            ?.substringBefore("?")
            ?.substringBefore("/")
            ?: "Unknown"

        Timber.d("screenName: $screenName")

        return screenName in bottomNavScreens
    }

    fun navigate(tab: MainTab) {
        val mainTabNavOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(NAVIGATION_ROOT) {
                    inclusive = true
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        val registerNavOptions = navOptions {
            launchSingleTop = true
        }

        if (tab != MainTab.REGISTER) {
            lastMainTab = tab
        }

        when (tab) {
            MainTab.MAP -> navController.navigateToMap(navOptions = mainTabNavOptions)
            MainTab.EXPLORE -> navController.navigateToExplore(mainTabNavOptions)
            MainTab.MYPAGE -> navController.navigateToMyPage(mainTabNavOptions)
            MainTab.REGISTER -> navController.navigateToRegister(
                registerType = RegisterType.CREATE,
                navOptions = registerNavOptions
            )
        }
    }

    fun navigateToSignIn(
        navOptions: NavOptions? = navOptions {
            popUpTo(NAVIGATION_ROOT) {
                inclusive = true
            }
        }
    ) {
        navController.navigateToSignIn(navOptions = navOptions)
    }

    fun navigateToTermsOfService(
        navOptions: NavOptions? = navOptions {
            popUpTo(NAVIGATION_ROOT) {
                inclusive = true
            }
        }
    ) {
        navController.navigateToTermsOfService(navOptions = navOptions)
    }

    fun navigateToOnboarding(
        navOptions: NavOptions? = navOptions {
            popUpTo(NAVIGATION_ROOT) {
                inclusive = true
            }
        }
    ) {
        navController.navigateToOnboarding(navOptions = navOptions)
    }

    fun navigateToMap(
        navOptions: NavOptions? = navOptions {
            popUpTo(NAVIGATION_ROOT) {
                inclusive = true
            }
        },
        locationId: Int? = null,
        locationName: String? = null,
        scale: String? = null,
        latitude: String? = null,
        longitude: String? = null
    ) {
        navController.navigateToMap(
            locationId = locationId,
            locationName = locationName,
            scale = scale,
            latitude = latitude,
            longitude = longitude,
            navOptions = navOptions
        )
    }

    fun navigateToReport(
        reportTargetId: Int,
        type: ReportType
    ) {
        navController.navigateToReport(reportTargetId = reportTargetId, type = type)
    }

    fun navigateToExplore(
        navOptions: NavOptions = navOptions {
            popUpTo(NAVIGATION_ROOT) {
                inclusive = true
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    ) {
        navController.navigateToExplore(navOptions)
    }

    fun navigateToRegister(
        navOptions: NavOptions = navOptions {
            launchSingleTop = true
        }
    ) {
        navController.navigateToRegister(navOptions = navOptions)
    }

    fun navigateToReviewEdit(
        postId: Int,
        registerType: RegisterType,
        navOptions: NavOptions = navOptions {
            launchSingleTop = true
        }
    ) {
        navController.navigateToRegister(
            registerType = registerType,
            postId = postId,
            navOptions = navOptions
        )
    }

    fun navigateToOtherPage(
        userId: Int
    ) {
        navController.navigateToOtherPage(userId = userId)
    }

    fun navigateToFollow(followType: FollowType, userId: Int) {
        navController.navigateToFollow(followType, userId)
    }

    fun navigateToProfileEdit(
        navOptions: NavOptions = navOptions {
            launchSingleTop = true
        }
    ) {
        navController.navigateToProfileEdit(navOptions)
    }

    fun navigateToMapSearch(navOptions: NavOptions? = null) {
        navController.navigateToMapSearch(navOptions)
    }

    fun navigateToPlaceDetail(
        postId: Int,
        navOptions: NavOptions? = null
    ) {
        navController.navigateToPlaceDetail(postId = postId, navOptions = navOptions)
    }

    fun navigateToExploreSearch(
        navOptions: NavOptions? = navOptions {
            launchSingleTop = true
            restoreState = true
        }
    ) {
        navController.navigateToExploreSearch(navOptions)
    }

    fun navigateToSettingPage(
        navOptions: NavOptions? = null
    ) {
        navController.navigateToSettingPage(
            navOptions = navOptions
        )
    }

    fun navigateToAttendance(
        navOptions: NavOptions? = null
    ) {
        navController.navigateToAttendance(
            navOptions = navOptions
        )
    }

    fun navigateToEnterTab() {
        navigate(lastMainTab)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    inline fun isCurrentDestination(destination: NavDestination): Boolean {
        return navController.currentDestination == destination
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
