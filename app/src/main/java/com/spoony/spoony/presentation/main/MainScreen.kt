package com.spoony.spoony.presentation.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var backPressedTime by remember {
        mutableLongStateOf(0L)
    }

    BackHandler(enabled = true) {
        if (System.currentTimeMillis() - backPressedTime <= 2000L) {
            (context as Activity).finish()
        } else {
            // TODO: 스낵바 자리
        }
        backPressedTime = System.currentTimeMillis()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            bottomBar = {
                MainBottomBar(
                    visible = navigator.shouldShowBottomBar(),
                    tabs = MainTab.entries.toPersistentList(),
                    currentTab = navigator.currentTab,
                    onTabSelected = {
                        navigator.navigate(it)
                    }
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
}
