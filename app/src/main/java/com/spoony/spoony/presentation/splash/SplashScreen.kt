package com.spoony.spoony.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.main400

@Composable
fun SplashRoute(
    navigateToMap: () -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(
            color = main400
        )

        if (viewModel.hasAccessToken()) {
            navigateToMap()
        } else {
            navigateToSignIn()
        }
    }

    SplashScreen()
}

@SuppressLint("CustomSplashScreen")
@Composable
private fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.main400),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.weight(1f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_tag_spoon_20),
                contentDescription = null,
                modifier = Modifier
                    .size(105.dp)
                    .padding(bottom = 8.dp)
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.img_spoony_logo_86),
                contentDescription = null
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )
    }
}
