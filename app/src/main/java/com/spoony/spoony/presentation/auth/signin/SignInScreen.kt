package com.spoony.spoony.presentation.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kakao.sdk.user.UserApiClient
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.main100
import com.spoony.spoony.presentation.auth.signin.component.SocialLoginButton

@Composable
fun SignInRoute(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit,
    navigateToTermsOfService: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val systemUiController = rememberSystemUiController()
    val showSnackbar = LocalSnackBarTrigger.current

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(
            color = main100
        )
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SignInSideEffect.ShowSnackBar -> showSnackbar(sideEffect.message)
                    is SignInSideEffect.NavigateToSignUp -> navigateToTermsOfService()
                    is SignInSideEffect.NavigateToMap -> navigateToMap()
                    is SignInSideEffect.StartKakaoTalkLogin -> {
                        UserApiClient.instance.loginWithKakaoTalk(
                            context = context,
                            callback = viewModel::handleSignInResult
                        )
                    }

                    is SignInSideEffect.StartKakaoWebLogin -> {
                        UserApiClient.instance.loginWithKakaoAccount(
                            context = context,
                            callback = viewModel::handleSignInResult
                        )
                    }
                }
            }
    }

    SignInScreen(
        paddingValues = paddingValues,
        onSignInButtonClick = {
            viewModel.startKakaoLogin(
                UserApiClient.instance.isKakaoTalkLoginAvailable(context)
            )
        }
    )
}

@Composable
private fun SignInScreen(
    paddingValues: PaddingValues,
    onSignInButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
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
                .padding(paddingValues)
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

            SocialLoginButton(
                title = "카카오로 계속하기",
                icon = ImageVector.vectorResource(R.drawable.ic_kakao_logo_18),
                backgroundColor = SpoonyAndroidTheme.colors.kakaoYellow,
                onClick = onSignInButtonClick
            )
        }
    }
}
