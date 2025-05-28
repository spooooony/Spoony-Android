package com.spoony.spoony.presentation.auth.signin

sealed class SignInSideEffect {
    data class ShowSnackBar(val message: String) : SignInSideEffect()
    data object NavigateToSignUp : SignInSideEffect()
    data object NavigateToMap : SignInSideEffect()
    data object StartKakaoTalkLogin : SignInSideEffect()
    data object StartKakaoWebLogin : SignInSideEffect()
}
