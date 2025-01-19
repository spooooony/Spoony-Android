package com.spoony.spoony.presentation.register.componet

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.util.UUID

const val MAX_PHOTO_COUNT = 5
const val MIN_PHOTO_COUNT = 2

data class SelectedPhoto(
    val uri: Uri,
    val id: String = UUID.randomUUID().toString()
)

@Composable
fun PhotoPicker(
    selectedPhotosList: ImmutableList<SelectedPhoto>,
    isErrorVisible: Boolean,
    onPhotosSelected: (ImmutableList<SelectedPhoto>) -> Unit
) {
    val remainingPhotoCount = MAX_PHOTO_COUNT - selectedPhotosList.size
    val isPhotoAddable = remainingPhotoCount > 0

    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = maxOf(remainingPhotoCount, MIN_PHOTO_COUNT))
    ) { uris ->
        onPhotosSelected((selectedPhotosList + uris.map { SelectedPhoto(it) }).toPersistentList())
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onPhotosSelected((selectedPhotosList + SelectedPhoto(uri)).toPersistentList())
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        val limitedUris = uris.take(remainingPhotoCount).map { SelectedPhoto(it) }
        onPhotosSelected((selectedPhotosList + limitedUris).toPersistentList())
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = SpoonyAndroidTheme.colors.gray100,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(
                            enabled = isPhotoAddable,
                            onClick = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    if (remainingPhotoCount == 1) {
                                        singlePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    } else {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                } else {
                                    galleryLauncher.launch("image/*")
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_24),
                            contentDescription = null,
                            tint = SpoonyAndroidTheme.colors.gray400,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${selectedPhotosList.size}/$MAX_PHOTO_COUNT",
                            style = SpoonyAndroidTheme.typography.caption1m,
                            color = SpoonyAndroidTheme.colors.gray400
                        )
                    }
                }
            }

            itemsIndexed(
                selectedPhotosList,
                key = { _, item -> item.id }
            ) { index, photo ->
                Box(
                    modifier = Modifier.size(80.dp)
                ) {
                    AsyncImage(
                        model = photo.uri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_filled_24),
                        contentDescription = null,
                        tint = SpoonyAndroidTheme.colors.gray400,
                        modifier = Modifier
                            .size(20.dp)
                            .noRippleClickable {
                                val newList = selectedPhotosList.toMutableList()
                                newList.removeAt(index)
                                onPhotosSelected(newList.toPersistentList())
                            }
                            .align(Alignment.TopEnd)
                            .padding(top = 4.dp, end = 4.dp)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = isErrorVisible,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut(),
        ) {
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.error400,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "사진 업로드는 필수예요",
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.error400
                )
            }
        }
    }
}

@Preview
@Composable
private fun PhotoPickerTestScreen() {
    var selectedPhotosList by remember { mutableStateOf<ImmutableList<SelectedPhoto>>(persistentListOf()) }
    var isPhotoEverUploaded by remember { mutableStateOf(false) }
    var isPhotoRequiredError by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(16.dp)
    ) {
        Text(
            text = "사진 업로드 테스트",
            style = SpoonyAndroidTheme.typography.body1m,
            color = SpoonyAndroidTheme.colors.gray900
        )

        Spacer(modifier = Modifier.height(24.dp))

        PhotoPicker(
            selectedPhotosList = selectedPhotosList,
            isErrorVisible = isPhotoRequiredError,
            onPhotosSelected = { photos ->
                selectedPhotosList = photos.toPersistentList()

                if (photos.isEmpty() && isPhotoEverUploaded) {
                    isPhotoRequiredError = true
                } else {
                    isPhotoRequiredError = false
                    isPhotoEverUploaded = true
                }
            }
        )
    }
}
