package com.spoony.spoony.presentation.userpage.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.ReviewRepository
import com.spoony.spoony.domain.repository.SpoonRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val spoonRepository: SpoonRepository,
    private val postRepository: PostRepository
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

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: SharedFlow<MyPageSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess { userInfo ->
                    _state.update { currentState ->
                        currentState.copy(profile = userInfo.toModel())
                    }
                    getMyReview()
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    fun getSpoonCount() {
        viewModelScope.launch {
            spoonRepository.getSpoonCount()
                .onSuccess { spoonCount ->
                    _state.update { currentState ->
                        currentState.copy(spoonCount = spoonCount)
                    }
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun getMyReview() {
        viewModelScope.launch {
            reviewRepository.getMyReview()
                .onSuccess { reviewEntity ->
                    _state.update { currentState ->
                        currentState.copy(reviews = reviewEntity.toModel())
                    }
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    fun deleteReview(reviewId: Int) {
        viewModelScope.launch {
            postRepository.deletePost(reviewId)
                .onSuccess {
                    getUserProfile()
                    _sideEffect.emit(MyPageSideEffect.ShowSnackbar("삭제 되었어요!"))
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }
}

sealed class MyPageSideEffect {
    data class ShowSnackbar(val message: String) : MyPageSideEffect()
    data class ShowError(val errorType: ErrorType) : MyPageSideEffect()
}
