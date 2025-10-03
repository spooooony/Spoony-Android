package com.spoony.spoony.presentation.splash

sealed class SplashSideEffect {
    data object NavigateToMap: SplashSideEffect()
    data object NavigateToSignIn: SplashSideEffect()
}
