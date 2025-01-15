package com.spoony.spoony.presentation.main

import androidx.compose.runtime.Composable
import com.spoony.spoony.R
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.explore.navigation.Explore
import com.spoony.spoony.presentation.map.navigaion.Map
import com.spoony.spoony.presentation.register.navigation.Register

enum class MainTab(
    val selectedIconResource: Int,
    val unselectedIconResource: Int,
    val contentDescription: String,
    val route: MainTabRoute
) {
    MAP(
        selectedIconResource = R.drawable.ic_map_main400_24,
        unselectedIconResource = R.drawable.ic_map_gray400_24,
        contentDescription = "내 지도",
        route = Map
    ),
    EXPLORE(
        selectedIconResource = R.drawable.ic_explore_main400_24,
        unselectedIconResource = R.drawable.ic_explore_gray400_24,
        contentDescription = "탐색",
        route = Explore
    ),
    REGISTER(
        selectedIconResource = R.drawable.ic_register_main400_24,
        unselectedIconResource = R.drawable.ic_register_gray400_24,
        contentDescription = "등록",
        route = Register
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
