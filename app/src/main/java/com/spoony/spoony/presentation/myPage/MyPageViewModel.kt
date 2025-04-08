package com.spoony.spoony.presentation.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MyPageState> = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState>
        get() = _state

    fun getUserProfile() {
        viewModelScope.launch {
            delay(500)
            val profile = UserProfile(
                imageUrl = "https://example.com/profile.jpg",
                nickname = "고졸 사토루",
                region = "서울 마포구 스푼",
                introduction = "두 사람은 문제아지만 최강.",
                reviewCount = 0,
                followerCount = 150,
                followingCount = 230
            )
            _state.update { it.copy(userProfile = profile) }
        }
    }

    fun getUserReviews() {}

    fun getSpoonCount() {
        viewModelScope.launch {
            authRepository.getSpoonCount()
                .onSuccess { count ->
                    _state.update { it.copy(spoonCount = count) }
                }
        }
    }
}
