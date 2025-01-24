package com.spoony.spoony.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun RegisterTooltip(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.padding(horizontal = 42.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, SpoonyAndroidTheme.colors.main400)
                .background(SpoonyAndroidTheme.colors.main400)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_tooltip_spoon_20),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.white
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_register_tooltip_arrow),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.offset(y = (-5).dp).padding(end = 12.dp)
        )
    }
}
