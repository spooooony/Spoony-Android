package com.spoony.spoony.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            tokenRepository.initCachedAccessToken()
        }
    }
    suspend fun hasAccessToken(): Boolean = tokenRepository.getAccessToken().first().isNotBlank()
}
