package com.spoony.spoony.core.designsystem.component.textField

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun SpoonyLineTextField(
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
    var selectText: Boolean by remember { mutableStateOf(false) }
    val spoonyColors = SpoonyAndroidTheme.colors

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
        SpoonyBasicTextField(
            value = value,
            placeholder = placeholder,
            onValueChanged = { newText ->
                isError = (newText.length > maxLength || newText.trim().length < minLength)
                selectText = newText.length > maxLength
                if (newText.length <= maxLength) {
                    onValueChanged(newText)
                }
            },
            borderColor = borderColor,
            modifier = modifier,
            onFocusChanged = {
                isFocused = it
                if (value.length >= minLength) {
                    isError = false
                }
            },
            trailingIcon = {
                Text(
                    text = "${value.length} / $maxLength",
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = subContentColor
                )
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
                (if (selectText) maxErrorText else minErrorText)?.let {
                    Text(
                        text = it,
                        style = SpoonyAndroidTheme.typography.caption1m,
                        color = subContentColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpoonyLineTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        SpoonyAndroidTheme {
            SpoonyLineTextField(
                value = text,
                placeholder = "장소명 언급은 피해주세요. 우리만의 비밀!",
                onValueChanged = { newText ->
                    text = newText
                },
                maxLength = 30,
                minLength = 5,
                minErrorText = "5글자 이상 입력해주세요",
                maxErrorText = "글자 수 30자 이하로 입력해 주세요"
            )
        }
    }
}
