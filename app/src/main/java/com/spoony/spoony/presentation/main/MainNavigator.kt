package com.spoony.spoony.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.spoony.spoony.presentation.explore.navigation.Explore
import com.spoony.spoony.presentation.explore.navigation.navigateToExplore
import com.spoony.spoony.presentation.map.navigaion.Map
import com.spoony.spoony.presentation.map.navigaion.navigateToMap
import com.spoony.spoony.presentation.map.search.navigation.navigateToMapSearch
import com.spoony.spoony.presentation.placeDetail.navigation.navigateToPlaceDetail
import com.spoony.spoony.presentation.register.navigation.navigateToRegister
import com.spoony.spoony.presentation.report.navigation.navigateToReport

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Map()

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.MAP -> navController.navigateToMap(navOptions = navOptions)
            MainTab.REGISTER -> navController.navigateToRegister(navOptions)
            MainTab.EXPLORE -> navController.navigateToExplore(navOptions)
        }
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }

    fun navigateToReport(
        postId: Int
    ) {
        navController.navigateToReport(postId = postId)
    }

    fun navigateToExplore() {
        val navOptions = navOptions {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        navController.navigateToExplore(navOptions)
    }

    fun navigateExploreToRegister(
        navOptions: NavOptions =
            navOptions {
                popUpTo(Explore) {
                    inclusive = true
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
    ) {
        navController.navigateToRegister(navOptions)
    }

    fun navigateToMapSearch(navOptions: NavOptions? = null) {
        navController.navigateToMapSearch(navOptions)
    }

    fun navigateToPlaceDetail(
        postId: Int,
        navOptions: NavOptions? = null
    ) {
        navController.navigateToPlaceDetail(postId = postId)
    }

    fun navigateToLocationMap(
        navOptions: NavOptions = navOptions {
            popUpTo(Map()) {
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
