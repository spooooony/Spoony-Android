package com.spoony.spoony.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.SpoonRepository
import com.spoony.spoony.domain.repository.TokenRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.splash.model.UserModel
import com.spoony.spoony.presentation.splash.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
    private val spoonRepository: SpoonRepository
) : ViewModel() {
    private var _state: MutableStateFlow<UserModel?> = MutableStateFlow(null)
    val state: StateFlow<UserModel?>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SplashSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<SplashSideEffect>
        get() = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            tokenRepository.initCachedAccessToken()

            if(hasAccessToken()) {
                getUserInfo()
            } else {
                _sideEffect.emit(SplashSideEffect.NavigateToSignIn)
            }
        }
    }

    private suspend fun hasAccessToken(): Boolean = tokenRepository.getAccessToken().first().isNotBlank()

    private fun getUserInfo() {
        viewModelScope.launch {
            val lastEnteredDate = spoonRepository.getSpoonDrawLog().first

            userRepository.getMyInfo()
                .onSuccess { response ->
                    _state.update {
                        response.toModel(lastEnteredDate)
                    }
                    _sideEffect.emit(SplashSideEffect.NavigateToMap)
                }
                .onLogFailure {
                    _sideEffect.emit(SplashSideEffect.NavigateToSignIn)
                }
        }
    }
}
