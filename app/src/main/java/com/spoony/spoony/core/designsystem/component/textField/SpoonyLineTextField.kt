package com.spoony.spoony.core.designsystem.component.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.TextFieldState

@Composable
fun SpoonyLineTextField(
    text: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    maxLength: Int? = null,
    helperText: String? = null,
    errorHelperText: String? = null,
    state: TextFieldState = TextFieldState.NORMAL
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextField(
            text = text,
            onValueChanged = onValueChanged,
            placeholder = placeholder,
            isError = isError,
            isFocused = isFocused,
            maxLength = maxLength,
            onFocusChanged = { focusState -> isFocused = focusState.isFocused },
            state = state
        )

        if ((state == TextFieldState.HELPER && helperText != null)) {
            HelperText(
                text = if (isError && errorHelperText != null) errorHelperText else helperText!!,
                isError = isError
            )
        }
    }
}

@Composable
private fun TextField(
    text: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    isError: Boolean,
    isFocused: Boolean,
    maxLength: Int?,
    onFocusChanged: (FocusState) -> Unit,
    state: TextFieldState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = getTextFieldColor(isError, isFocused),
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = SpoonyAndroidTheme.colors.white,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                if (maxLength == null || newText.length <= maxLength) {
                    onValueChanged(newText)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .onFocusChanged(onFocusChanged),
            textStyle = SpoonyAndroidTheme.typography.body2m.copy(
                color = SpoonyAndroidTheme.colors.gray900
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box {
                    if (text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = SpoonyAndroidTheme.colors.gray500,
                            style = SpoonyAndroidTheme.typography.body2m
                        )
                    }
                    innerTextField()
                }
            }
        )

        if ((state == TextFieldState.COUNT || state == TextFieldState.HELPER) && maxLength != null) {
            TextCounter(
                current = text.length,
                max = maxLength,
                isError = isError,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
            )
        }
    }
}

@Composable
private fun HelperText(
    text: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Info, //추후 아이콘 변경
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = getHelperTextColor(isError)
        )
        Text(
            text = text,
            style = SpoonyAndroidTheme.typography.caption1m,
            color = getHelperTextColor(isError)
        )
    }
}

@Composable
private fun TextCounter(
    current: Int,
    max: Int,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$current / $max",
        style = SpoonyAndroidTheme.typography.caption1m,
        color = getHelperTextColor(isError),
        textAlign = TextAlign.Center,
        modifier = modifier.background(SpoonyAndroidTheme.colors.white)
    )
}

@Composable
private fun getTextFieldColor(
    isError: Boolean,
    isFocused: Boolean
): Color = when {
    isError -> SpoonyAndroidTheme.colors.error400
    isFocused -> SpoonyAndroidTheme.colors.main400
    else -> SpoonyAndroidTheme.colors.gray100
}

@Composable
private fun getHelperTextColor(isError: Boolean): Color =
    if (isError) SpoonyAndroidTheme.colors.error400
    else SpoonyAndroidTheme.colors.gray500


@Preview(showBackground = true)
@Composable
private fun SpoonyLineTextFieldPreview() {
    SpoonyAndroidTheme {
        var text by remember { mutableStateOf("") }
        var isError by rememberSaveable { mutableStateOf(false) }
        isError = text.length > 20

        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            SpoonyLineTextField(
                text = text,
                onValueChanged = { text = it },
                placeholder = "플레이스 홀더",
                maxLength = 30,
                isError = isError,
                state = TextFieldState.NORMAL
            )
            SpoonyLineTextField(
                text = text,
                onValueChanged = { text = it },
                placeholder = "플레이스 홀더",
                maxLength = 30,
                isError = isError,
                state = TextFieldState.COUNT
            )
            SpoonyLineTextField(
                text = text,
                onValueChanged = { text = it },
                placeholder = "플레이스 홀더",
                maxLength = 30,
                isError = isError,
                helperText = "코멘트",
                errorHelperText = "20자 이상이면 에러나요",
                state = TextFieldState.HELPER
            )
        }
    }
}

