package com.spoony.spoony.presentation.myPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spoony.spoony.core.designsystem.theme.white

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(white)
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MyPageScreen")
    }
}
