package com.spoony.spoony.core.designsystem.component.textField

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    onDoneAction: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }

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
                painter = painterResource(id = R.drawable.ic_search_24),
                contentDescription = null,
                tint = SpoonyAndroidTheme.colors.gray600,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(16.dp)

            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete_filled_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray400,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(16.dp)
                        .noRippleClickable(
                            onClick = {
                                onValueChanged("")
                            }
                        )
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneAction?.invoke()
            }
        )
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
            onDoneAction = {
                Log.d(text, "Enter key pressed with text: $text")
            }
        )
    }
}
