package com.spoony.spoony.presentation.auth.onboarding

import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.designsystem.model.RegionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<OnboardingState> = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState>
        get() = _state.asStateFlow()

    fun updateNickname(nickname: String) {
        _state.update {
            it.copy(nickname = nickname)
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
            it.copy(currentStep = step.step)
        }
    }
}
