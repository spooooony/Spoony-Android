package com.spoony.spoony.presentation.setting

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spoony.spoony.presentation.setting.account.AccountDeleteScreen
import com.spoony.spoony.presentation.setting.account.AccountManagementScreen
import com.spoony.spoony.presentation.setting.block.BlockUserScreen
import com.spoony.spoony.presentation.setting.main.SettingMainRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.serialization.Serializable

@Composable
fun SettingRoute(
    navigateUp: () -> Unit,
) {
    val settingNavigator: NavHostController = rememberNavController()
    val context = LocalContext.current

    val eventChannel = remember { Channel<String>(Channel.BUFFERED) }

    LaunchedEffect(Unit) {
        for (event in eventChannel) {
            val intent = Intent(Intent.ACTION_VIEW, event.toUri())

            context.startActivity(intent)
        }
    }

    NavHost(
        navController = settingNavigator,
        startDestination = SettingRoutes.Main
    ) {
        composable<SettingRoutes.Main> {
            SettingMainRoute(
                navigateUp = navigateUp,
                navigateToSettingRoute = settingNavigator::navigate,
                navigateToWebView = eventChannel::trySend
            )
        }

        composable<SettingRoutes.BlockUser> {
            BlockUserScreen(
                navigateUp = settingNavigator::navigateUp
            )
        }

        composable<SettingRoutes.AccountManagement> {
            AccountManagementScreen(
                navigateUp = settingNavigator::navigateUp,
                navigateToDeleteAccount = settingNavigator::navigate
            )
        }

        composable<SettingRoutes.AccountDelete> {
            AccountDeleteScreen(
                navigateUp = settingNavigator::navigateUp
            )
        }
    }
}

internal sealed interface SettingRoutes {
    @Serializable
    data object Main : SettingRoutes

    @Serializable
    data object BlockUser : SettingRoutes

    @Serializable
    data object AccountManagement : SettingRoutes

    @Serializable
    data object AccountDelete : SettingRoutes

    data class Web(val url: String) : SettingRoutes
}
