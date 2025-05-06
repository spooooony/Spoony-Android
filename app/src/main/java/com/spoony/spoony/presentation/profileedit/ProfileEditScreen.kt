package com.spoony.spoony.presentation.profileedit

import BirthSelectButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyDatePickerBottomSheet
import com.spoony.spoony.core.designsystem.component.button.RegionSelectButton
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyNicknameTextField
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.explore.component.bottomsheet.ExploreLocationBottomSheet
import com.spoony.spoony.presentation.profileedit.component.ImageHelperBottomSheet
import com.spoony.spoony.presentation.profileedit.component.ProfileImageList
import com.spoony.spoony.presentation.profileedit.model.ProfileImageModel
import kotlinx.collections.immutable.toPersistentList

val profileImageList = (1..6).map { i ->
    ProfileImageModel(
        imageLevel = i,
        imageUrl = "https://avatars.githubusercontent.com/u/160750136?v=$i",
        name = "Image $i",
        description = "Description for Image $i",
        isSelected = i == 1,
        isUnLocked = i < 4
    )
}.toPersistentList()

@Composable
fun ProfileEditScreen(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isImageBottomSheetVisible by remember { mutableStateOf(false) }
    var isDateBottomSheetVisible by remember { mutableStateOf(false) }
    var isLocationBottomSheetVisible by remember { mutableStateOf(false) }

    var nickname by remember { mutableStateOf("톳시") }
    var nicknameState by remember { mutableStateOf(NicknameTextFieldState.DEFAULT) }
    var introduction by remember { mutableStateOf("오타쿠의 패왕") }
    var selectedLevel by remember { mutableIntStateOf(1) }
    var selectedYear by remember { mutableStateOf("2000") }
    var selectedMonth by remember { mutableStateOf("01") }
    var selectedDay by remember { mutableStateOf("01") }
    var isBirthSelected by remember { mutableStateOf(false) }
    var selectedRegion by remember { mutableStateOf("서울 마포구") }
    var isRegionSelected by remember { mutableStateOf(false) }

    var saveButtonEnabled by remember { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .addFocusCleaner(focusManager)
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
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        ProfileImageList(
            profileImages = profileImageList,
            selectedLevel = selectedLevel,
            onSelectLevel = { level ->
                selectedLevel = level
            }
        )

        Spacer(modifier = Modifier.height(41.dp))

        SubSection(text = "닉네임을 입력해 주세요") {
            SpoonyNicknameTextField(
                value = nickname,
                onValueChanged = {
                    nickname = it
                    saveButtonEnabled = if (nicknameState == NicknameTextFieldState.DEFAULT) {
                        true 
                    } else {
                        nicknameState == NicknameTextFieldState.AVAILABLE && it.isNotBlank()
                    }
                },
                onStateChanged = { 
                    nicknameState = it
                    saveButtonEnabled = if (it == NicknameTextFieldState.DEFAULT) {
                        true 
                    } else {
                        it == NicknameTextFieldState.AVAILABLE && nickname.isNotBlank()
                    }
                },
                placeholder = "스푼의 이름을 정해주세요 (한글, 영문, 숫자 입력 가능)",
                onDoneAction = { },
                maxLength = 10,
                minLength = 1,
                state = nicknameState,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SubSection(text = "간단한 자기소개를 입력해 주세요") {
            SpoonyLargeTextField(
                value = introduction,
                onValueChanged = { introduction = it },
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
                year = selectedYear,
                month = selectedMonth,
                day = selectedDay,
                isBirthSelected = isBirthSelected
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        SubSection(text = "주로 활동하는 지역을 설정해 주세요") {
            RegionSelectButton(
                onClick = { isLocationBottomSheetVisible = true },
                region = selectedRegion,
                isSelected = isRegionSelected
            )

        }

        Spacer(modifier = Modifier.height(38.dp))

        SaveButton(
            enabled = saveButtonEnabled,
            onClick = onBackButtonClick,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(38.dp))


    }

    if (isImageBottomSheetVisible) {
        ImageHelperBottomSheet(
            onDismiss = { isImageBottomSheetVisible = false },
            profileImageList = profileImageList
        )
    }

    if (isDateBottomSheetVisible) {
        SpoonyDatePickerBottomSheet(
            onDismiss = { isDateBottomSheetVisible = false },
            onDateSelected = { date ->
                val parts = date.split("-")
                if (parts.size == 3) {
                    selectedYear = parts[0]
                    selectedMonth = parts[1]
                    selectedDay = parts[2]
                    isBirthSelected = true
                }
            },
            initialYear = selectedYear.toIntOrNull() ?: 2000,
            initialMonth = selectedMonth.toIntOrNull() ?: 1,
            initialDay = selectedDay.toIntOrNull() ?: 1
        )

    }

    if (isLocationBottomSheetVisible) {
        ExploreLocationBottomSheet(
            onDismiss = { isLocationBottomSheetVisible = false },
            onClick = { region ->
                selectedRegion = region
                isRegionSelected = true
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
