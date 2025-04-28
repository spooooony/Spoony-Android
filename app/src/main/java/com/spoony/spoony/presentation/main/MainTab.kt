package com.spoony.spoony.presentation.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.spoony.spoony.R
import com.spoony.spoony.core.navigation.MainTabRoute
import com.spoony.spoony.core.navigation.Route
import com.spoony.spoony.presentation.explore.navigation.Explore
import com.spoony.spoony.presentation.gourmet.map.navigaion.Map
import com.spoony.spoony.presentation.register.navigation.Register
import com.spoony.spoony.presentation.userpage.mypage.navigation.MyPage

enum class MainTab(
    @DrawableRes val selectedIconResource: Int,
    @DrawableRes val unselectedIconResource: Int,
    val label: String,
    val route: MainTabRoute
) {
    MAP(
        selectedIconResource = R.drawable.ic_map_main400_24,
        unselectedIconResource = R.drawable.ic_map_gray400_24,
        label = "내 지도",
        route = Map()
    ),
    EXPLORE(
        selectedIconResource = R.drawable.ic_explore_main400_24,
        unselectedIconResource = R.drawable.ic_explore_gray400_24,
        label = "탐색",
        route = Explore
    ),
    REGISTER(
        selectedIconResource = R.drawable.ic_register_main400_24,
        unselectedIconResource = R.drawable.ic_register_gray400_24,
        label = "등록",
        route = Register
    ),
    MYPAGE(
        selectedIconResource = R.drawable.ic_mypage_main400_24,
        unselectedIconResource = R.drawable.ic_mypage_gray400_24,
        label = "마이",
        route = MyPage
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
