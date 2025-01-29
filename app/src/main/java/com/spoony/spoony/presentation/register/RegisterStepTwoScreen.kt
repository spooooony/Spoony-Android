package com.spoony.spoony.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.dialog.SingleButtonDialog
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLineTextField
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MAX_DETAIL_REVIEW_LENGTH
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MAX_ONE_LINE_REVIEW_LENGTH
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MIN_DETAIL_REVIEW_LENGTH
import com.spoony.spoony.presentation.register.component.NextButton
import com.spoony.spoony.presentation.register.component.PhotoPicker
import com.spoony.spoony.presentation.register.component.SelectedPhoto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun RegisterStepTwoScreen(
    onStepTwoComplete: () -> Unit,
    onRegisterComplete: () -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    var isDialogVisible by remember { mutableStateOf(false) }
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoony_register_get))

    val isNextButtonEnabled = remember(
        state.oneLineReview,
        state.detailReview,
        state.selectedPhotos,
        state.isLoading
    ) {
        viewModel.checkSecondStepValidation() && !state.isLoading
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

        Spacer(modifier = Modifier.height(32.dp))

        ReviewSection(
            oneLineReview = state.oneLineReview,
            detailReview = state.detailReview,
            onOneLineReviewChange = viewModel::updateOneLineReview,
            onDetailReviewChange = viewModel::updateDetailReview
        )

        Spacer(modifier = Modifier.height(32.dp))

        PhotoSection(
            selectedPhotos = state.selectedPhotos,
            isPhotoErrorVisible = state.isPhotoErrorVisible,
            onPhotosSelected = viewModel::updatePhotos
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(24.dp)
        )

        NextButton(
            enabled = isNextButtonEnabled,
            onClick = {
                viewModel.registerPost {
                    onStepTwoComplete()
                    isDialogVisible = true
                }
            }
        )
    }

    if (isDialogVisible) {
        SingleButtonDialog(
            message = "수저 1개를 획득했어요!\n이제 새로운 장소를 떠먹으러 가볼까요?",
            text = "좋아요!",
            buttonStyle = ButtonStyle.Primary,
            onDismiss = {
                onRegisterComplete()
                isDialogVisible = false
            },
            onClick = {
                onRegisterComplete()
                isDialogVisible = false
            }
        ) {
            LottieAnimation(
                modifier = Modifier.sizeIn(minHeight = 150.dp),
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}

@Composable
private fun ReviewSection(
    oneLineReview: String,
    detailReview: String,
    onOneLineReviewChange: (String) -> Unit,
    onDetailReviewChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Text(
            text = "당신의 맛집을 한 줄로 표현해 주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        SpoonyLineTextField(
            value = oneLineReview,
            onValueChanged = onOneLineReviewChange,
            placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
            maxLength = MAX_ONE_LINE_REVIEW_LENGTH,
            minLength = 1,
            minErrorText = "한 줄 소개는 필수예요",
            maxErrorText = "글자 수 30자 이하로 입력해 주세요"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "자세한 후기를 적어주세요",
                style = SpoonyAndroidTheme.typography.body1sb,
                color = SpoonyAndroidTheme.colors.black
            )

            Spacer(modifier = Modifier.width(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_register_main400_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.main400,
                    modifier = Modifier.size(6.dp)
                )
                Text(
                    text = "50자 이상",
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.main400
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        SpoonyLargeTextField(
            value = detailReview,
            onValueChanged = onDetailReviewChange,
            placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
            maxLength = MAX_DETAIL_REVIEW_LENGTH,
            minLength = MIN_DETAIL_REVIEW_LENGTH,
            minErrorText = "자세한 후기는 필수예요",
            maxErrorText = "글자 수 500자 이하로 입력해 주세요"
        )
    }
}

@Composable
private fun PhotoSection(
    selectedPhotos: ImmutableList<SelectedPhoto>,
    isPhotoErrorVisible: Boolean,
    onPhotosSelected: (List<SelectedPhoto>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "음식 사진을 올려주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        PhotoPicker(
            selectedPhotosList = selectedPhotos.toPersistentList(),
            isErrorVisible = isPhotoErrorVisible,
            onPhotosSelected = onPhotosSelected
        )
    }
}
