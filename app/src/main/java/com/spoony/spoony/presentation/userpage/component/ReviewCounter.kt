package com.spoony.spoony.presentation.userpage.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.black
import com.spoony.spoony.core.designsystem.theme.gray400

@Composable
fun ReviewCounter(
    reviewCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "리뷰",
            style = SpoonyAndroidTheme.typography.body1b,
            color = black
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${reviewCount}개",
            style = SpoonyAndroidTheme.typography.body2m,
            color = gray400
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewCounterPreview() {
    SpoonyAndroidTheme {
        ReviewCounter(reviewCount = 10)
    }
}
