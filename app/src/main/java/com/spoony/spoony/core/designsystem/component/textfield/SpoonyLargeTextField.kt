package com.spoony.spoony.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun SpoonyLargeTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    minLength: Int = 0,
    minErrorText: String? = null,
    maxErrorText: String? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    var isError: Boolean by remember { mutableStateOf(false) }
    var isOverflowed: Boolean by remember { mutableStateOf(false) }
    val spoonyColors = SpoonyAndroidTheme.colors
    val errorText = if (isOverflowed) maxErrorText else minErrorText
    val borderColor = remember(isError, isFocused) {
        when {
            isError -> spoonyColors.error400
            isFocused -> spoonyColors.main400
            else -> spoonyColors.gray100
        }
    }

    val subContentColor = remember(isError) {
        when {
            isError -> spoonyColors.error400
            else -> spoonyColors.gray500
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CustomBasicTextField(
            value = value,
            placeholder = placeholder,
            onValueChanged = { newText ->
                isError = (newText.length > maxLength || newText.trim().length < minLength)
                isOverflowed = newText.length > maxLength
                if (newText.length <= maxLength) {
                    onValueChanged(newText)
                }
            },
            maxLength = maxLength,
            borderColor = borderColor,
            subContentColor = subContentColor,
            modifier = modifier,
            onFocusChanged = {
                isFocused = it
                if (value.trim().length >= minLength) {
                    isError = false
                }
            }
        )

        if (((minErrorText != null || maxErrorText != null) && isError)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error_24),
                    contentDescription = null,
                    tint = subContentColor,
                    modifier = Modifier.size(16.dp)
                )
                if (errorText != null) {
                    Text(
                        text = errorText,
                        style = SpoonyAndroidTheme.typography.caption1m,
                        color = subContentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomBasicTextField(
    value: String,
    placeholder: String,
    onValueChanged: (String) -> Unit,
    borderColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    subContentColor: Color,
    modifier: Modifier = Modifier,
    maxLength: Int = 0,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white,
    imeAction: ImeAction = ImeAction.Done,
    singleLine: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val counterText = stringResource(R.string.COUNTER_TEXT, value.length, maxLength)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (SpoonyValidator.isNotContainsEmoji(newValue)) {
                    onValueChanged(newValue)
                }
            },
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    borderColor,
                    RoundedCornerShape(8.dp)
                )
                .background(backgroundColor)
                .padding(horizontal = 12.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            textStyle = SpoonyAndroidTheme.typography.body2m.copy(
                color = SpoonyAndroidTheme.colors.gray900
            ),
            decorationBox = { innerTextField ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        innerTextField()
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = SpoonyAndroidTheme.colors.gray500,
                                style = SpoonyAndroidTheme.typography.body2m
                            )
                        }
                    }
                    Text(
                        text = counterText,
                        style = SpoonyAndroidTheme.typography.caption1m,
                        color = subContentColor,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SpoonyLargeTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        SpoonyAndroidTheme {
            SpoonyLargeTextField(
                value = text,
                placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
                onValueChanged = { newText ->
                    text = newText
                },
                maxLength = 500,
                minLength = 50,
                minErrorText = "자세한 후기는 필수예요",
                maxErrorText = "글자 수 500자 이하로 입력해 주세요"
            )
        }
    }
}
