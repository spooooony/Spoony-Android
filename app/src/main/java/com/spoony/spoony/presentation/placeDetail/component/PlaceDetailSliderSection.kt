package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.spoony.spoony.core.designsystem.component.slider.SpoonySlider
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun PlaceDetailSliderSection(
    sliderPosition: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "가격 대비 만족도는 어땠나요?",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        SpoonySlider(
            value = sliderPosition,
            onValueChange = {},
            enabled = false
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "아쉬움",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black
            )

            Text(
                text = "적당함",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black
            )

            Text(
                text = "훌륭함",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black
            )
        }
    }
}
