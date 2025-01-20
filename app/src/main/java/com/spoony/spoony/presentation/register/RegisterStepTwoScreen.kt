package com.spoony.spoony.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLineTextField
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.register.component.MIN_PHOTO_COUNT
import com.spoony.spoony.presentation.register.component.NextButton
import com.spoony.spoony.presentation.register.component.PhotoPicker
import com.spoony.spoony.presentation.register.component.SelectedPhoto
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun RegisterStepTwoScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    var oneLineReview by remember { mutableStateOf("") }
    var detailReview by remember { mutableStateOf("") }
    var selectedPhotosList by remember { mutableStateOf<PersistentList<SelectedPhoto>>(persistentListOf()) }
    var isPhotoErrorVisible by remember { mutableStateOf(false) }

    val isNextButtonEnabled = remember(oneLineReview, detailReview, selectedPhotosList) {
        oneLineReview.trim().length in 1..30 &&
            detailReview.trim().length >= 50 &&
            selectedPhotosList.size >= MIN_PHOTO_COUNT
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .verticalScroll(rememberScrollState())
            .padding(top = 22.dp, bottom = 17.dp)
    ) {
        Text(
            text = "거의 다 왔어요!",
            style = SpoonyAndroidTheme.typography.title2b,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "당신의 맛집을 한 줄로 표현해 주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        SpoonyLineTextField(
            value = oneLineReview,
            onValueChanged = { oneLineReview = it },
            placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
            maxLength = 30,
            minLength = 1,
            minErrorText = "한 줄 소개는 필수예요",
            maxErrorText = "글자 수 30자 이하로 입력해 주세요"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "자세한 후기를 적어주세요",
                style = SpoonyAndroidTheme.typography.body1sb,
                color = SpoonyAndroidTheme.colors.black
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_register_main400_24),
                contentDescription = null,
                tint = SpoonyAndroidTheme.colors.main400,
                modifier = Modifier.size(6.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "50자 이상",
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.main400
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SpoonyLargeTextField(
            value = detailReview,
            onValueChanged = { detailReview = it },
            placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
            maxLength = 500,
            minLength = 50,
            minErrorText = "자세한 후기는 필수예요",
            maxErrorText = "글자 수 500자 이하로 입력해 주세요"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "음식 사진을 올려주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        PhotoPicker(
            selectedPhotosList = selectedPhotosList,
            isErrorVisible = isPhotoErrorVisible,
            onPhotosSelected = { photos ->
                selectedPhotosList = photos.toPersistentList()
                isPhotoErrorVisible = photos.isEmpty()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        NextButton(
            enabled = isNextButtonEnabled,
            onClick = onComplete,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}
