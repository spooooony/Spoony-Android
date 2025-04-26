package com.spoony.spoony.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ReportRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        TitleTopAppBar(
            title = "설정",
            onBackButtonClick = navigateUp,
        )

        SettingSection(
            title = "계정",
            items = persistentListOf(
                SettingItem("계정 관리", {}),
            )
        )

        SettingSection(
            title = "기타",
            items = persistentListOf(
                SettingItem("차단한 유저 관리", {}),
                SettingItem("서비스 이용약관", {}),
                SettingItem("개인정보 처리 방침", {}),
                SettingItem("위치기반서비스 이용약관", {}),
                SettingItem("1:1 문의", {}),
            )
        )
    }
}

data class SettingItem(
    val title: String,
    val onClick: () -> Unit,
)

@Composable
fun SettingSection(
    title: String,
    items: ImmutableList<SettingItem>,
) {
    Column {
        DividerTitle(title = title)
        items.forEach { item ->
            TempItem(
                title = item.title,
                onClick = item.onClick
            )
        }
    }
}

@Composable
fun DividerTitle(
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
fun TempItem(
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .clickable(onClick = onClick),
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
private fun ReportRoutePreview() {
    SpoonyAndroidTheme {
        Scaffold(
            content = { paddingValues ->
                ReportRoute(
                    paddingValues = paddingValues,
                    navigateUp = {}
                )
            }
        )
    }
}
