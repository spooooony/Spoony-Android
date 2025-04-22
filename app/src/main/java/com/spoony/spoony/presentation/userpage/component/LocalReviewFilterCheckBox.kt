package com.spoony.spoony.presentation.userpage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.main400

@Composable
fun LocalReviewFilterCheckBox(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val checkBoxIcon = if (isSelected) R.drawable.ic_checkbox_main400 else R.drawable.ic_checkbox_gray400

    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = checkBoxIcon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(26.dp)
        )

        Text(
            text = "로컬리뷰",
            style = SpoonyAndroidTheme.typography.body2b,
            color = main400
        )
    }
}
