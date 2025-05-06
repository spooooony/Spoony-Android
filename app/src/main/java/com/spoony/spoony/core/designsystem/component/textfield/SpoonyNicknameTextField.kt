package com.spoony.spoony.core.designsystem.component.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chattymin.pebble.containsEmoji
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.core.util.extension.noRippleClickable
import timber.log.Timber

enum class NicknameTextFieldState(val text: String) {
    DEFAULT(""),
    AVAILABLE("사용 가능한 닉네임이에요"),
    DUPLICATE("이미 사용 중인 닉네임이에요"),
    INVALID_CHARACTERS("닉네임은 한글,영어,숫자만 사용할 수 있어요"),
    OVERFLOW_MAX_LENGTH("10자 이하로 입력해주세요"),
    NICKNAME_REQUIRED("닉네임은 필수예요")
}

@Composable
fun SpoonyNicknameTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    onDoneAction: () -> Unit,
    onStateChanged: (NicknameTextFieldState) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int = 10,
    minLength: Int = 1,
    state: NicknameTextFieldState = NicknameTextFieldState.DEFAULT
) {
    var isFirstEntry by remember { mutableStateOf(true) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val spoonyColors = SpoonyAndroidTheme.colors
    val counterText = stringResource(R.string.COUNTER_TEXT, value.length, maxLength)

    val (borderColor, textColor) = remember(state) {
        when (state) {
            NicknameTextFieldState.DEFAULT -> spoonyColors.gray100 to spoonyColors.gray500
            NicknameTextFieldState.AVAILABLE -> spoonyColors.green400 to spoonyColors.green400
            else -> spoonyColors.error400 to spoonyColors.error400
        }
    }

    Column(
        modifier = modifier
            .addFocusCleaner(focusManager)
    ) {
        SpoonyBasicTextField(
            value = value,
            onValueChanged = { newText ->
                when {
                    newText.containsEmoji() || !SpoonyValidator.isNotContainsSpecialChars(newText) -> {
                        onStateChanged(NicknameTextFieldState.INVALID_CHARACTERS)
                    }

                    newText.length > maxLength -> {
                        onStateChanged(NicknameTextFieldState.OVERFLOW_MAX_LENGTH)
                    }

                    newText.trim().length < minLength -> {
                        Timber.d("stateChanged minlength $newText")
                        onStateChanged(NicknameTextFieldState.NICKNAME_REQUIRED)
                        onValueChanged(newText)
                    }

                    else -> {
                        Timber.d("stateChanged else $newText")
                        onStateChanged(NicknameTextFieldState.DEFAULT)
                        onValueChanged(newText)
                    }
                }
            },
            placeholder = placeholder,
            borderColor = borderColor,
            imeAction = ImeAction.Done,
            onFocusChanged = {
                if (isFirstEntry) {
                    onStateChanged(NicknameTextFieldState.DEFAULT)
                    isFirstEntry = false
                } else if (value.trim().length < minLength) {
                    onStateChanged(NicknameTextFieldState.NICKNAME_REQUIRED)
                } else {
                    onDoneAction()
                }
            },
            onDoneAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
                if (state != NicknameTextFieldState.NICKNAME_REQUIRED) onDoneAction()
            },
            singleLine = true,
            trailingIcon = {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_delete_filled_24),
                        contentDescription = null,
                        tint = spoonyColors.gray400,
                        modifier = Modifier
                            .noRippleClickable {
                                onValueChanged("")
                                onStateChanged(NicknameTextFieldState.NICKNAME_REQUIRED)
                            }
                    )
                }
            },
            isAllowEmoji = true,
            isAllowSpecialChars = true
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            if (state != NicknameTextFieldState.DEFAULT) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (state == NicknameTextFieldState.AVAILABLE) R.drawable.ic_check_16 else R.drawable.ic_error_24
                    ),
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = state.text,
                style = SpoonyAndroidTheme.typography.caption1m,
                color = textColor,
                modifier = Modifier
                    .weight(1f)
            )

            Text(
                text = counterText,
                style = SpoonyAndroidTheme.typography.caption1m,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpoonyNicknameTextFieldPreview() {
    SpoonyAndroidTheme {
        var text by remember { mutableStateOf("") }
        var state by remember { mutableStateOf(NicknameTextFieldState.DEFAULT) }

        SpoonyNicknameTextField(
            value = text,
            onValueChanged = { text = it },
            onStateChanged = { state = it },
            placeholder = "플레이스홀더",
            onDoneAction = { state = NicknameTextFieldState.DUPLICATE },
            maxLength = 10,
            minLength = 1,
            state = state,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
