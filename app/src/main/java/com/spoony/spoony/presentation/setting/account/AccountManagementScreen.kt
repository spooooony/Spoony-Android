package com.spoony.spoony.presentation.setting.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jakewharton.processphoenix.ProcessPhoenix
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.presentation.setting.SettingRoutes

@Composable
internal fun AccountManagementScreen(
    navigateUp: () -> Unit,
    navigateToDeleteAccount: (SettingRoutes) -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.restartTrigger, lifecycleOwner) {
        viewModel.restartTrigger.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            ProcessPhoenix.triggerRebirth(context)
        }
    }

    var isShowDialog by remember { mutableStateOf(false) }

    if (isShowDialog) {
        TwoButtonDialog(
            message = "로그아웃 하시겠습니까?",
            negativeText = "아니요",
            onClickNegative = { isShowDialog = false },
            positiveText = "네",
            onClickPositive = viewModel::signOut,
            onDismiss = {
                // Nothing To Do
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray0),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TitleTopAppBar(
            title = "계정 관리",
            onBackButtonClick = navigateUp
        )

        Row(
            modifier = Modifier
                .background(white)
                .padding(vertical = 14.dp, horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "간편 로그인",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray700
            )
            Text(
                text = "카카오 로그인 사용 중",
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.gray600
            )
        }

        Text(
            text = "로그아웃",
            style = SpoonyAndroidTheme.typography.body2m,
            modifier = Modifier
                .background(white)
                .clickable { isShowDialog = true }
                .padding(14.dp)
                .fillMaxWidth(),
            color = SpoonyAndroidTheme.colors.main400,
            textAlign = TextAlign.Center
        )

        Text(
            text = "탈퇴하기",
            style = SpoonyAndroidTheme.typography.body2m,
            modifier = Modifier
                .background(white)
                .clickable { navigateToDeleteAccount(SettingRoutes.AccountDelete) }
                .padding(14.dp)
                .fillMaxWidth(),
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun AccountManagementPreview() {
    SpoonyAndroidTheme {
        AccountManagementScreen(
            navigateUp = { },
            navigateToDeleteAccount = { }
        )
    }
}
