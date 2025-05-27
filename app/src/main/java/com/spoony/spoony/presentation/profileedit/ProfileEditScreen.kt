package com.spoony.spoony.presentation.profileedit

import BirthSelectButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyDatePickerBottomSheet
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyRegionBottomSheet
import com.spoony.spoony.core.designsystem.component.button.RegionSelectButton
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyNicknameTextField
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.profileedit.component.ImageHelperBottomSheet
import com.spoony.spoony.presentation.profileedit.component.ProfileImageList

@Composable
fun ProfileEditScreen(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileEditViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val showSnackBar = LocalSnackBarTrigger.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isImageBottomSheetVisible by remember { mutableStateOf(false) }
    var isDateBottomSheetVisible by remember { mutableStateOf(false) }
    var isLocationBottomSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is ProfileEditSideEffect.ShowSnackBar -> {
                    showSnackBar(sideEffect.message)
                }

                is ProfileEditSideEffect.ShowError -> {
                    showSnackBar(sideEffect.errorType.description)
                }

                ProfileEditSideEffect.NavigateBack -> {
                    onBackButtonClick()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .addFocusCleaner(focusManager)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    if (state.nickname.trim().isNotEmpty()) {
                        viewModel::checkNicknameDuplication
                    }
                }
            }
    ) {
        TitleTopAppBar(
            onBackButtonClick = onBackButtonClick
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "프로필 이미지",
                style = SpoonyAndroidTheme.typography.title3sb,
                color = SpoonyAndroidTheme.colors.black
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_question_24),
                modifier = Modifier.clickable { isImageBottomSheetVisible = true },
                tint = Color.Unspecified,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        ProfileImageList(
            profileImages = state.profileImages,
            selectedLevel = state.selectedImageLevel,
            onSelectLevel = viewModel::selectImageLevel

        )

        Spacer(modifier = Modifier.height(41.dp))

        SubSection(text = "닉네임을 입력해 주세요") {
            SpoonyNicknameTextField(
                value = state.nickname,
                onValueChanged = viewModel::updateNickname,
                onStateChanged = viewModel::updateNicknameState,
                placeholder = "스푼의 이름을 정해주세요 (한글, 영문, 숫자 입력 가능)",
                onDoneAction = viewModel::checkNicknameDuplication,
                maxLength = 10,
                minLength = 1,
                state = state.nicknameState
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SubSection(text = "간단한 자기소개를 입력해 주세요") {
            SpoonyLargeTextField(
                value = state.introduction,
                onValueChanged = viewModel::updateIntroduction,
                placeholder = "안녕! 나는 어떤 스푼이냐면...",
                maxLength = 50,
                minLength = 0,
                maxErrorText = "최대 50자까지 입력 가능해요.",
                decorationBoxHeight = 20.dp,
                isAllowSpecialChars = true
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SubSection(text = "생년월일을 입력해 주세요") {
            BirthSelectButton(
                onClick = { isDateBottomSheetVisible = true },
                modifier = Modifier,
                year = state.selectedYear,
                month = state.selectedMonth,
                day = state.selectedDay,
                isBirthSelected = state.isBirthSelected
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SubSection(text = "주로 활동하는 지역을 설정해 주세요") {
            RegionSelectButton(
                onClick = { isLocationBottomSheetVisible = true },
                region = state.selectedRegion,
                isSelected = state.isRegionSelected
            )
        }

        Spacer(modifier = Modifier.height(38.dp))

        SaveButton(
            enabled = state.saveButtonEnabled,
            onClick = viewModel::updateProfileInfo,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(38.dp))
    }

    if (isImageBottomSheetVisible) {
        ImageHelperBottomSheet(
            onDismiss = { isImageBottomSheetVisible = false },
            profileImageList = state.profileImages
        )
    }

    if (isDateBottomSheetVisible) {
        SpoonyDatePickerBottomSheet(
            onDismiss = { isDateBottomSheetVisible = false },
            onDateSelected = { date ->
                val parts = date.split("-")
                if (parts.size == 3) {
                    viewModel.selectDate(parts[0], parts[1], parts[2])
                }
            },
            initialYear = state.selectedYear.toIntOrNull() ?: 2000,
            initialMonth = state.selectedMonth.toIntOrNull() ?: 1,
            initialDay = state.selectedDay.toIntOrNull() ?: 1
        )
    }

    if (isLocationBottomSheetVisible) {
        SpoonyRegionBottomSheet(
            regionList = state.regionList,
            onDismiss = { isLocationBottomSheetVisible = false },
            onClick = { region ->
                viewModel.selectRegion(region.regionId, region.regionName)
            }
        )
    }
}

@Composable
private fun SubSection(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        content()
    }
}

@Composable
private fun SaveButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false) }

    SpoonyButton(
        text = "저장",
        size = ButtonSize.Xlarge,
        style = ButtonStyle.Primary,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled && !isClicked,
        onClick = {
            if (!isClicked) {
                isClicked = true
                onClick()
            }
        }
    )
}
