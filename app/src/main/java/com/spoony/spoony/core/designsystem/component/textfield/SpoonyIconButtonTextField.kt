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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun SpoonyIconButtonTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    showDeleteIcon: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    helperText: String? = null,
    isAllowEmoji: Boolean = false,
    isAllowSpecialChars: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    val spoonyColors = SpoonyAndroidTheme.colors
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val borderColor = remember(isFocused) {
        when {
            isFocused -> spoonyColors.main400
            else -> spoonyColors.gray100
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SpoonyBasicTextField(
            value = value,
            placeholder = placeholder,
            onValueChanged = { newText ->
                if (newText.length <= maxLength) {
                    onValueChanged(newText)
                }
            },
            borderColor = borderColor,
            modifier = modifier,
            imeAction = ImeAction.Done,
            onDoneAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            onFocusChanged = { isFocused = it },
            trailingIcon = {
                if (showDeleteIcon) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_minus_gray400_24),
                        contentDescription = null,
                        tint = spoonyColors.gray400,
                        modifier = Modifier.noRippleClickable(onClick = onDeleteClick)
                    )
                }
            },
            isAllowEmoji = isAllowEmoji,
            isAllowSpecialChars = isAllowSpecialChars
        )

        if (helperText != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_24),
                    contentDescription = null,
                    tint = spoonyColors.gray500,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = helperText,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = spoonyColors.gray500
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpoonyIconButtonTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    SpoonyAndroidTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            SpoonyIconButtonTextField(
                value = text,
                onValueChanged = { text = it },
                placeholder = "플레이스 홀더",
                onDeleteClick = { text = "" },
                maxLength = 30,
                showDeleteIcon = text2.isNotEmpty()
            )
            SpoonyIconButtonTextField(
                value = text2,
                onValueChanged = { text2 = it },
                placeholder = "플레이스 홀더",
                onDeleteClick = { text2 = "" },
                maxLength = 30,
                helperText = "헬퍼 코멘트입니다"
            )
        }
    }
}
