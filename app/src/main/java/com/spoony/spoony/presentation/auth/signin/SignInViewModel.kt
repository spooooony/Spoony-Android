package com.spoony.spoony.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel @Inject constructor() : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SignInSideEffect>()
    val sideEffect: SharedFlow<SignInSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun startKakaoLogin(isKakaoLoginAvailable: Boolean) {
        viewModelScope.launch {
            if (isKakaoLoginAvailable) {
                _sideEffect.emit(SignInSideEffect.StartKakaoTalkLogin)
            } else {
                _sideEffect.emit(SignInSideEffect.StartKakaoWebLogin)
            }
        }
    }

    fun handleSignInResult(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            when {
                token != null -> {
                    // 로그인 성공
                }

                error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                    // 사용자가 로그인을 취소한 경우
                    _sideEffect.emit(SignInSideEffect.ShowSnackBar("카카오 로그인을 취소했습니다."))
                }

                error is AuthError -> {
                    // 인증 인가 오류 -> 웹으로 재시도
                    _sideEffect.emit(SignInSideEffect.StartKakaoWebLogin)
                }

                else -> {
                    _sideEffect.emit(SignInSideEffect.ShowSnackBar("카카오 로그인에 실패했습니다."))
                }
            }
        }
    }
}
