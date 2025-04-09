package com.spoony.spoony.presentation.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.main100
import com.spoony.spoony.presentation.auth.signin.component.KakaoButton

@Composable
fun SignInRoute(
    navigateToMap: () -> Unit,
    navigateToTermsOfService: () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = main100
        )
    }

    SignInScreen(navigateToMap = navigateToMap)
}

@Composable
private fun SignInScreen(
    navigateToMap: () -> Unit
) {
    Image(
        painter = painterResource(R.drawable.img_login_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = 144.dp, bottom = 36.dp)
            .padding(horizontal = 20.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_spoony_text_logo),
            contentDescription = null
        )

        Text(
            text = "스푼에서 시작되는 찐맛의 여정",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.main400,
            modifier = Modifier
                .padding(top = 10.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        KakaoButton(
            onClick = navigateToMap
        )
    }
}
