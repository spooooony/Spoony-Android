package com.spoony.spoony.presentation.profileedit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.presentation.profileedit.component.ImageHelperBottomSheet
import com.spoony.spoony.presentation.profileedit.component.ProfileImageModel
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ProfileEditScreen(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profileImageList = (1..6).map { i ->
        ProfileImageModel(
            imageUrl = "https://avatars.githubusercontent.com/u/160750136?v=$i",
            name = "Image $i",
            description = "Description for Image $i"
        )
    }.toPersistentList()

    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(white),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleTopAppBar(
            onBackButtonClick = onBackButtonClick
        )

        Surface(
            modifier = Modifier,
            color = main400,
            contentColor = white,
            onClick = { isBottomSheetVisible = true }
        ) {
            Text(
                text = "테스트 버튼",
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    if (isBottomSheetVisible) {
        ImageHelperBottomSheet(
            onDismiss = { isBottomSheetVisible = false },
            profileImageList = profileImageList
        )
    }
}
