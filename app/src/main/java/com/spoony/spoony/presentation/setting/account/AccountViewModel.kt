package com.spoony.spoony.presentation.setting.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val accessToken: String
        get() = tokenRepository.getCachedAccessToken()

    private val _restartTrigger = MutableSharedFlow<Unit>()
    val restartTrigger: SharedFlow<Unit>
        get() = _restartTrigger.asSharedFlow()

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut(
                token = accessToken
            ).onSuccess {
                tokenRepository.clearTokens()
                _restartTrigger.emit(Unit)
            }.onLogFailure {
                // TODO: 언젠가 에러처리 하기
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            authRepository.withDraw(
                token = accessToken
            ).onSuccess {
                tokenRepository.clearTokens()
                _restartTrigger.emit(Unit)
            }.onLogFailure {
                // TODO: 언젠가 에러처리 하기
            }
        }
    }
}
