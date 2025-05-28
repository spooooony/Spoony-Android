package com.spoony.spoony.presentation.setting.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    val accessToken: String
        get() =  tokenRepository.getCachedAccessToken()

    val restartTrigger = MutableSharedFlow<Unit>()

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut(
                token = accessToken
            ).onSuccess {
                tokenRepository.clearTokens()
                restartTrigger.emit(Unit)
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
                async {
                    tokenRepository.clearTokens()
                }.await()

                restartTrigger.emit(Unit)
            }.onLogFailure {
                // TODO: 언젠가 에러처리 하기
            }
        }
    }
}
