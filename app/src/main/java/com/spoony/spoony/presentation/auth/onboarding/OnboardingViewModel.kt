package com.spoony.spoony.presentation.auth.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<OnboardingState> = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<OnboardingSideEffect>()
    val sideEffect: SharedFlow<OnboardingSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun skipStep() {
        when (_state.value.currentStep) {
            OnboardingSteps.TWO -> {
                _state.update {
                    it.copy(
                        birth = null,
                        region = null
                    )
                }
            }

            OnboardingSteps.THREE -> {
                _state.update {
                    it.copy(
                        introduction = null
                    )
                }
            }

            else -> {}
        }
    }

    fun checkUserNameExist() {
        viewModelScope.launch {
            userRepository.checkUserNameExist(_state.value.nickname)
                .onSuccess {
                    if (it) {
                        updateNicknameState(NicknameTextFieldState.DUPLICATE)
                    } else {
                        updateNicknameState(NicknameTextFieldState.AVAILABLE)
                    }
                }
                .onLogFailure {
                    updateNicknameState(NicknameTextFieldState.DEFAULT)
                    _sideEffect.emit(OnboardingSideEffect.ShowSnackbar(ErrorType.SERVER_CONNECTION_ERROR.description))
                }
        }
    }

    fun getRegionList() {
        _state.update {
            it.copy(regionList = UiState.Loading)
        }
        viewModelScope.launch {
            userRepository.getRegionList()
                .onSuccess { regionList ->
                    _state.update {
                        it.copy(
                            regionList = UiState.Success(
                                regionList.map { region ->
                                    RegionModel(
                                        regionId = region.regionId,
                                        regionName = region.regionName
                                    )
                                }.toImmutableList()
                            )
                        )
                    }
                }
                .onLogFailure {
                    _state.update {
                        it.copy(
                            regionList = UiState.Failure(ErrorType.SERVER_CONNECTION_ERROR.description)
                        )
                    }
                }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            with(_state.value) {
                signUpUseCase(
                    platform = "KAKAO",
                    userName = nickname,
                    birth = birth,
                    regionId = region?.regionId,
                    introduction = introduction
                ).onSuccess {
                    _state.update {
                        it.copy(signUpState = UiState.Empty)
                    }
                }.onLogFailure {
                    _state.update {
                        it.copy(signUpState = UiState.Failure(ErrorType.SERVER_CONNECTION_ERROR.description))
                    }
                }
            }
        }
    }

    fun updateNickname(nickname: String) {
        _state.update {
            it.copy(nickname = nickname)
        }
    }

    fun updateNicknameState(state: NicknameTextFieldState) {
        _state.update {
            it.copy(nicknameState = state)
        }
    }

    fun updateBirth(birth: String) {
        _state.update {
            it.copy(birth = birth)
        }
    }

    fun updateRegion(region: RegionModel) {
        _state.update {
            it.copy(region = region)
        }
    }

    fun updateIntroduction(introduction: String) {
        _state.update {
            it.copy(introduction = introduction)
        }
    }

    fun updateCurrentStep(step: OnboardingSteps) {
        _state.update {
            it.copy(currentStep = step)
        }
    }
}
