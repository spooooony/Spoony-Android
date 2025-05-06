package com.spoony.spoony.presentation.setting.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.setting.SettingRoutes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel

@Composable
internal fun SettingMainRoute(
    navigateUp: () -> Unit,
    navigateToSettingRoute: (SettingRoutes) -> Unit,
    navigateToWebView: (String) -> Unit
) {
    val eventChannel = remember { Channel<SettingRoutes>(Channel.BUFFERED) }

    LaunchedEffect(Unit) {
        for (route in eventChannel) {
            when (route) {
                is SettingRoutes.Web -> navigateToWebView(route.url)
                else -> navigateToSettingRoute(route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TitleTopAppBar(
            title = "설정",
            onBackButtonClick = navigateUp,
        )

        SettingSection(
            title = "계정",
            items = persistentListOf(
                SettingItem("계정 관리", SettingRoutes.AccountManagement),
            ),
            onClick = eventChannel::trySend
        )

        SettingSection(
            title = "기타",
            items = persistentListOf(
                SettingItem("차단한 유저 관리", SettingRoutes.BlockUser),
                SettingItem("서비스 이용약관", SettingRoutes.Web("https://github.com/Hyobeen-Park")),
                SettingItem("개인정보 처리 방침", SettingRoutes.Web("https://github.com/angryPodo")),
                SettingItem("위치기반서비스 이용약관", SettingRoutes.Web("https://github.com/Roel4990")),
                SettingItem("1:1 문의", SettingRoutes.Web("https://github.com/chattymin")),
            ),
            onClick = eventChannel::trySend
        )
    }
}

private data class SettingItem(
    val title: String,
    val url: SettingRoutes
)

@Composable
private fun SettingSection(
    title: String,
    items: ImmutableList<SettingItem>,
    onClick: (SettingRoutes) -> Unit
) {
    Column {
        DividerTitle(title = title)
        items.forEach { item ->
            TempItem(
                title = item.title,
                onClick = {
                    onClick(item.url)
                }
            )
        }
    }
}

@Composable
private fun DividerTitle(
    title: String,
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier
                .background(SpoonyAndroidTheme.colors.gray0)
                .padding(start = 20.dp)
                .padding(vertical = 9.dp),
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.gray600
        )
    }
}

@Composable
private fun TempItem(
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray700
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_24),
            tint = SpoonyAndroidTheme.colors.gray700,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun SettingMainRoutePreview() {
    SpoonyAndroidTheme {
        SettingMainRoute(
            navigateUp = {},
            navigateToSettingRoute = {},
            navigateToWebView = {}
        )
    }
}
