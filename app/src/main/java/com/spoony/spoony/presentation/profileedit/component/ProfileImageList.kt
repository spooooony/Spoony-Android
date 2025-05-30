package com.spoony.spoony.presentation.profileedit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.profileedit.model.ProfileImageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ProfileImageList(
    profileImages: ImmutableList<ProfileImageModel>,
    selectedLevel: Int,
    onSelectLevel: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(
            items = profileImages,
            key = { profileImage -> profileImage.imageLevel }
        ) { profileImage ->
            ProfileImage(
                imageUrl = profileImage.imageUrl,
                isUnLocked = profileImage.isUnLocked,
                isSelected = profileImage.imageLevel == selectedLevel,
                onClick = {
                    if (profileImage.isUnLocked) {
                        onSelectLevel(profileImage.imageLevel)
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileImage(
    imageUrl: String,
    isUnLocked: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageModifier = Modifier
        .size(90.dp)
        .clip(CircleShape)
        .clickable(onClick = onClick)
        .then(
            if (isUnLocked && isSelected) {
                Modifier.border(
                    width = 4.dp,
                    color = SpoonyAndroidTheme.colors.main400,
                    shape = CircleShape
                )
            } else {
                Modifier
            }
        )

    Box(modifier = modifier) {
        UrlImage(
            imageUrl = imageUrl,
            modifier = imageModifier,
            shape = CircleShape
        )

        if (!isUnLocked) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(90.dp)
                    .background(color = SpoonyAndroidTheme.colors.black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    modifier = Modifier.size(23.dp),
                    tint = SpoonyAndroidTheme.colors.white,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileImageListPrev() {
    val selectedLevel = remember { mutableIntStateOf(1) }

    val sampleImages = (1..4).map { level ->
        ProfileImageModel(
            imageLevel = level,
            imageUrl = "",
            isSelected = level == 1,
            isUnLocked = level < 4,
            spoonName = "imageName"
        )
    }.toPersistentList()

    SpoonyAndroidTheme {
        ProfileImageList(
            profileImages = sampleImages,
            selectedLevel = selectedLevel.value,
            onSelectLevel = { level ->
                selectedLevel.value = level
            }
        )
    }
}
