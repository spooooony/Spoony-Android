package com.spoony.spoony.presentation.profileedit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicBottomSheet
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ProfileImageModel(
    val imageUrl: String,
    val name: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageHelperBottomSheet(
    onDismiss: () -> Unit,
    profileImageList: ImmutableList<ProfileImageModel>,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    SpoonyBasicBottomSheet(
        onDismiss = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        dragHandle = { ImageHelperDragHandle(onClick = onDismiss) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            profileImageList.forEach { model ->
                key(model.imageUrl) {
                    ProfileImageCard(
                        imageUrl = model.imageUrl,
                        name = model.name,
                        description = model.description
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageHelperBottomSheetPreview() {
    val profileImageList = persistentListOf(
        ProfileImageModel(
            imageUrl = "https://example.com/image1.jpg",
            name = "Image 1",
            description = "Description for Image 1"
        ),
        ProfileImageModel(
            imageUrl = "https://example.com/image2.jpg",
            name = "Image 2",
            description = "Description for Image 2"
        ),
        ProfileImageModel(
            imageUrl = "https://example.com/image3.jpg",
            name = "Image 3",
            description = "Description for Image 3"
        ),
        ProfileImageModel(
            imageUrl = "https://example.com/image4.jpg",
            name = "Image 4",
            description = "Description for Image 4"
        ),
        ProfileImageModel(
            imageUrl = "https://example.com/image5.jpg",
            name = "Image 5",
            description = "Description for Image 5"
        ),
        ProfileImageModel(
            imageUrl = "https://example.com/image6.jpg",
            name = "Image 6",
            description = "Description for Image 6"
        )

    )

    SpoonyAndroidTheme {
        var isBottomSheetVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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

        if (isBottomSheetVisible) ImageHelperBottomSheet(
            onDismiss = { isBottomSheetVisible = false },
            profileImageList = profileImageList
        )
    }
}
