package com.spoony.spoony.presentation.userpage.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state: MutableStateFlow<UserPageState> = MutableStateFlow(
        UserPageState(
            userType = UserType.MY_PAGE,
            profile = UserProfile(),
            spoonCount = 0
        )
    )
    val state: StateFlow<UserPageState>
        get() = _state.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val profile = UserProfile(
                profileId = 4,
                imageUrl = "https://avatars.githubusercontent.com/u/160750136?v=4",
                nickname = "톳시",
                region = "에도 막부",
                introduction = "오타쿠의 패왕",
                reviewCount = 0,
                followerCount = 150,
                followingCount = 230
            )
            _state.update { it.copy(profile = profile) }
        }
    }

    fun getSpoonCount() {
        viewModelScope.launch {
            authRepository.getSpoonCount()
                .onSuccess { count ->
                    _state.update { it.copy(spoonCount = count) }
                }
        }
    }
}
