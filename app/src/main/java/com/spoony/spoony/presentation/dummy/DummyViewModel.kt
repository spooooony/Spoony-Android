package com.spoony.spoony.presentation.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.DummyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val dummyRepository: DummyRepository
) : ViewModel() {
    var state: MutableStateFlow<DummyState> =
        MutableStateFlow(DummyState())
        private set

    fun getDummyUser(page: Int) {
        viewModelScope.launch {
            dummyRepository.getDummyUser(page = page)
                .onSuccess { response ->
                    state.update {
                        it.copy(
                            user = UiState.Success(response)
                        )
                    }
                }
        }
    }
}
