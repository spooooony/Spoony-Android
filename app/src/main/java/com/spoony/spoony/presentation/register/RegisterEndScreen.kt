package com.spoony.spoony.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.dialog.SingleButtonDialog
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.core.util.extension.advancedImePadding
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MAX_DETAIL_REVIEW_LENGTH
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MAX_OPTIONAL_REVIEW_LENGTH
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MIN_DETAIL_REVIEW_LENGTH
import com.spoony.spoony.presentation.register.component.NextButton
import com.spoony.spoony.presentation.register.component.PhotoPicker
import com.spoony.spoony.presentation.register.component.SelectedPhoto
import com.spoony.spoony.presentation.register.model.RegisterState
import com.spoony.spoony.presentation.register.model.RegisterType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun RegisterEndRoute(
    onRegisterComplete: () -> Unit,
    onEditComplete: (postId: Int) -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val registerType = viewModel.registerType

    val isNextButtonEnabled = remember(
        state.detailReview,
        state.selectedPhotos,
        state.isLoading
    ) {
        viewModel.checkSecondStepValidation() && !state.isLoading
    }

    RegisterEndScreen(
        registerType = registerType,
        state = state,
        isNextButtonEnabled = isNextButtonEnabled,
        onDetailReviewChange = viewModel::updateDetailReview,
        onPhotosSelected = viewModel::updatePhotos,
        onOptionalReviewChange = viewModel::updateOptionalReview,
        onRegisterPost = viewModel::registerPost,
        onRegisterComplete = onRegisterComplete,
        onEditComplete = onEditComplete,
        postId = viewModel.postId,
        modifier = modifier
    )
}

@Composable
private fun RegisterEndScreen(
    registerType: RegisterType,
    state: RegisterState,
    isNextButtonEnabled: Boolean,
    onDetailReviewChange: (String) -> Unit,
    onPhotosSelected: (ImmutableList<SelectedPhoto>) -> Unit,
    onOptionalReviewChange: (String) -> Unit,
    onRegisterPost: (onSuccess: () -> Unit) -> Unit,
    onRegisterComplete: () -> Unit,
    onEditComplete: (postId: Int) -> Unit,
    postId: Int?,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var isDialogVisible by remember { mutableStateOf(false) }

    val onNextClick = getOnNextClick(
        registerType = registerType,
        postId = postId,
        onRegisterPost = onRegisterPost,
        onEditComplete = onEditComplete,
        onShowDialog = { isDialogVisible = true }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .advancedImePadding()
            .verticalScroll(rememberScrollState())
            .padding(top = 22.dp, bottom = 17.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "거의 다 왔어요!",
            style = SpoonyAndroidTheme.typography.title3b,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(32.dp))

        ReviewSection(
            detailReview = state.detailReview,
            onDetailReviewChange = onDetailReviewChange
        )

        Spacer(modifier = Modifier.height(40.dp))

        PhotoSection(
            selectedPhotos = state.selectedPhotos,
            isPhotoErrorVisible = state.isPhotoErrorVisible,
            onPhotosSelected = onPhotosSelected
        )

        Spacer(modifier = Modifier.height(32.dp))

        OptionalReviewSection(
            optionalReview = state.optionalReview,
            onOptionalReviewChange = onOptionalReviewChange
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(31.dp))

        NextButton(
            enabled = isNextButtonEnabled,
            onClick = onNextClick,
            editText = getNextButtonText(registerType)
        )
    }

    if (isDialogVisible && registerType == RegisterType.CREATE) {
        SingleButtonDialog(
            message = "수저 1개를 획득했어요!\n이제 새로운 장소를 떠먹으러 가볼까요?",
            text = "좋아요!",
            buttonStyle = ButtonStyle.Primary,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            ),
            onDismiss = {},
            onClick = {
                onRegisterComplete()
                isDialogVisible = false
            }
        ) {
            LottieAnimation(
                modifier = Modifier.sizeIn(minHeight = 150.dp),
                composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoony_register_get)).value,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}

@Composable
private fun ReviewSection(
    detailReview: String,
    onDetailReviewChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "자세한 후기를 적어주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        SpoonyLargeTextField(
            value = detailReview,
            onValueChanged = onDetailReviewChange,
            placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
            maxLength = MAX_DETAIL_REVIEW_LENGTH,
            minLength = MIN_DETAIL_REVIEW_LENGTH,
            minErrorText = "자세한 후기는 필수예요",
            maxErrorText = "글자 수 ${MAX_DETAIL_REVIEW_LENGTH}자 이하로 입력해 주세요",
            isAllowEmoji = true,
            isAllowSpecialChars = true
        )
    }
}

@Composable
private fun PhotoSection(
    selectedPhotos: ImmutableList<SelectedPhoto>,
    isPhotoErrorVisible: Boolean,
    onPhotosSelected: (ImmutableList<SelectedPhoto>) -> Unit,
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

@Composable
private fun OptionalReviewSection(
    optionalReview: String,
    onOptionalReviewChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "딱 한 가지 아쉬운 점은?",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        SpoonyLargeTextField(
            value = optionalReview,
            onValueChanged = onOptionalReviewChange,
            placeholder = "사장님 몰래 솔직 후기!\n이 내용은 비공개 처리돼요!(선택)",
            maxLength = MAX_OPTIONAL_REVIEW_LENGTH,
            maxErrorText = "글자 수 ${MAX_OPTIONAL_REVIEW_LENGTH}자 이하로 입력해 주세요",
            decorationBoxHeight = 80.dp,
            isAllowEmoji = true,
            isAllowSpecialChars = true
        )
    }
}

private fun getNextButtonText(registerType: RegisterType): String =
    when (registerType) {
        RegisterType.CREATE -> "다음"
        RegisterType.EDIT -> "리뷰 수정"
    }

@Composable
private fun getOnNextClick(
    registerType: RegisterType,
    postId: Int?,
    onRegisterPost: ((onSuccess: () -> Unit) -> Unit),
    onEditComplete: (Int) -> Unit,
    onShowDialog: () -> Unit
): () -> Unit = {
    onRegisterPost {
        when (registerType) {
            RegisterType.EDIT -> postId?.let(onEditComplete)
            RegisterType.CREATE -> onShowDialog()
        }
    }
}
