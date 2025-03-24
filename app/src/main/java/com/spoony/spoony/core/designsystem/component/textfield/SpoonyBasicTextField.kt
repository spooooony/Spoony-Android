package com.spoony.spoony.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun SpoonyBasicTextField(
    value: String,
    placeholder: String,
    onValueChanged: (String) -> Unit,
    borderColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white,
    imeAction: ImeAction = ImeAction.Done,
    onDoneAction: () -> Unit = {},
    onSearchAction: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    singleLine: Boolean = true,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    isFilterEmoji: Boolean = true,
    isFilterSpecialChars: Boolean = true
) {
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            val filteredValue = newValue.takeIf {
                (!isFilterEmoji || SpoonyValidator.isNotContainsEmoji(it)) &&
                        (!isFilterSpecialChars || SpoonyValidator.isNotContainsSpecialChars(it))
            } ?: value

            onValueChanged(filteredValue)
        },
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> onFocusChanged(focusState.isFocused) },
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = { onDoneAction() },
            onSearch = { onSearchAction() }
        ),
        textStyle = SpoonyAndroidTheme.typography.body2m.copy(
            color = SpoonyAndroidTheme.colors.gray900
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                leadingIcon()
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp)
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
                trailingIcon()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SpoonyBasicTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    SpoonyAndroidTheme {
        SpoonyBasicTextField(
            value = text,
            placeholder = "플레이스 홀더",
            onValueChanged = { newText ->
                text = newText
            },
            borderColor = SpoonyAndroidTheme.colors.gray100,
            onFocusChanged = { focused ->
                isFocused = focused
            }
        )
    }
}
