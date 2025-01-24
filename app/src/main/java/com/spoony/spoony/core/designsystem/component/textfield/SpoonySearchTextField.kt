package com.spoony.spoony.core.designsystem.component.textfield

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
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
fun SpoonySearchTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    focusRequester: FocusRequester = FocusRequester(),
    onSearchAction: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    SpoonyBasicTextField(
        value = value,
        placeholder = placeholder,
        onValueChanged = { newText ->
            if (newText.length <= maxLength) {
                onValueChanged(newText)
            }
        },
        borderColor = SpoonyAndroidTheme.colors.gray100,
        modifier = modifier,
        onFocusChanged = { isFocused = it },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search_24),
                contentDescription = null,
                tint = SpoonyAndroidTheme.colors.gray600,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(20.dp)

            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_filled_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray400,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(20.dp)
                        .noRippleClickable(
                            onClick = {
                                onValueChanged("")
                            }
                        )
                )
            }
        },
        imeAction = ImeAction.Search,
        focusRequester = focusRequester,
        onSearchAction = {
            onSearchAction()
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    )
}

@Preview
@Composable
private fun SpoonySearchTextFieldPreview() {
    SpoonyAndroidTheme {
        var text by remember { mutableStateOf("") }

        SpoonySearchTextField(
            value = text,
            onValueChanged = { text = it },
            placeholder = "플레이스 홀더",
            maxLength = 30,
            onSearchAction = {
                Log.d(text, "Enter key pressed with text: $text")
            }
        )
    }
}
