package com.spoony.spoony.presentation.main

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.spoony.spoony.presentation.explore.navigation.exploreNavGraph
import com.spoony.spoony.presentation.main.component.MainBottomBar
import com.spoony.spoony.presentation.map.navigaion.mapNavGraph
import com.spoony.spoony.presentation.register.navigation.registerNavGraph
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator()
) {
    val context = LocalContext.current

    SpoonyBackHandler(context = context)

    Scaffold(
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            mapNavGraph()

            exploreNavGraph(
                paddingValues = innerPadding
            )

            registerNavGraph(
                paddingValues = innerPadding
            )
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
