package com.spoony.spoony.presentation.setting.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.presentation.setting.block.component.BlockUserItem

@Composable
fun BlockUserScreen(
    navigateUp: () -> Unit,
    viewModel: BlockUserViewModel = hiltViewModel()
) {
    val blockUserList by viewModel.blockingList.collectAsStateWithLifecycle()
    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.errorEvent, lifecycleOwner) {
        viewModel.errorEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collect { errorMessage ->
            showSnackBar(errorMessage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        TitleTopAppBar(
            title = "차단한 유저",
            onBackButtonClick = navigateUp
        )

        HorizontalDivider(
            thickness = 10.dp,
            color = SpoonyAndroidTheme.colors.gray0
        )

        LazyColumn {
            itemsIndexed(
                items = blockUserList,
                key = { _, item -> item.userId }
            ) { index, user ->
                BlockUserItem(
                    imageUrl = user.imageUrl,
                    userName = user.userName,
                    region = if(user.region.isNullOrBlank()) "" else "서울 ${user.region} 스푼",
                    isBlocking = user.isBlocking,
                    onBlockButtonClick = {
                        viewModel.onClickBlockButton(user.userId, user.isBlocking)
                    },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = SpoonyAndroidTheme.colors.gray0
                )
            }
        }
    }
}

@Preview
@Composable
private fun BlockUserRoutePreview() {
    SpoonyAndroidTheme {
        BlockUserScreen(
            navigateUp = {}
        )
    }
}
