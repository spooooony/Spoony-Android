package com.spoony.spoony.presentation.map.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun MapSearchEmptyResultScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 72.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(220 / 100f)
                .padding(horizontal = 50.dp)
        )
        Text(
            text = "검색결과가 없어요",
            style = SpoonyAndroidTheme.typography.body2sb,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .padding(top = 24.dp)
        )
        Text(
            text = "정확한 지명(구/동),\n지하철역을 입력해 보세요",
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}
