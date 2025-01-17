package com.spoony.spoony.presentation.report.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.report.ReportOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

@Composable
fun ReportRadioButton(
    selectedOption: ReportOption,
    onOptionSelected: (ReportOption) -> Unit,
    options: ImmutableList<ReportOption>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        options.forEach { option ->
            key(option) {
                Row(
                    modifier = Modifier.padding(vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.size(24.dp),
                        selected = selectedOption == option,
                        onClick = { onOptionSelected(option) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = SpoonyAndroidTheme.colors.black,
                            unselectedColor = SpoonyAndroidTheme.colors.gray400
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = option.displayName,
                        modifier = Modifier.noRippleClickable { onOptionSelected(option) },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportRadioButtonPreview() {
    val options = immutableListOf(*ReportOption.entries.toTypedArray())
    var selectedOption by remember { mutableStateOf(ReportOption.COMMERCIAL) }

    SpoonyAndroidTheme {
        ReportRadioButton(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it },
            options = options
        )
    }
}
