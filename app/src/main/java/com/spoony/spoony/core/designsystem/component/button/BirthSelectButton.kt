import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun BirthSelectButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    year: String = "2000",
    month: String = "01",
    day: String = "01",
    isBirthSelected: Boolean = false
) {
    val textColor = if (isBirthSelected) SpoonyAndroidTheme.colors.black else SpoonyAndroidTheme.colors.gray500

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .noRippleClickable(onClick = onClick)
    ) {
        DateItem(
            date = year,
            text = "년",
            textColor = textColor
        )

        DateItem(
            date = month,
            text = "월",
            textColor = textColor
        )

        DateItem(
            date = day,
            text = "일",
            textColor = textColor
        )
    }
}

@Composable
private fun DateItem(
    date: String,
    text: String,
    textColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            style = SpoonyAndroidTheme.typography.body1m,
            color = textColor,
            modifier = Modifier
                .border(
                    color = SpoonyAndroidTheme.colors.gray100,
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(
                    vertical = 10.dp,
                    horizontal = 24.dp
                )
        )

        Text(
            text = text,
            style = SpoonyAndroidTheme.typography.body1m,
            color = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectBirthButtonPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            BirthSelectButton(
                onClick = {}
            )

            BirthSelectButton(
                onClick = {},
                year = "2001",
                month = "09",
                day = "26",
                isBirthSelected = true,
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        }
    }
}
