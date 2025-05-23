package com.spoony.spoony.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
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
                token != null -> signIn(token)

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

    private fun signIn(
        token: OAuthToken,
        platform: String = "KAKAO"
    ) {
        viewModelScope.launch {
            signInUseCase(
                token = token.accessToken,
                platform = platform
            ).onSuccess { token ->
                if (token == null) {
                    _sideEffect.emit(SignInSideEffect.NavigateToSignUp)
                } else {
                    _sideEffect.emit(SignInSideEffect.NavigateToMap)
                }
            }.onLogFailure {
                _sideEffect.emit(SignInSideEffect.ShowSnackBar("예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."))
            }
        }
    }
}
