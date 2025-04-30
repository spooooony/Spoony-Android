package com.spoony.spoony.presentation.profileedit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicBottomSheet
import kotlinx.collections.immutable.ImmutableList

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
