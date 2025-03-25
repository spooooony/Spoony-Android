package com.spoony.spoony.presentation.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.ReportRepository
import com.spoony.spoony.presentation.report.navigation.Report
import com.spoony.spoony.presentation.report.type.ReportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableStateFlow<ReportState> = MutableStateFlow(ReportState())
    val state: StateFlow<ReportState>
        get() = _state

    private val _sideEffect = MutableSharedFlow<ReportSideEffect>()
    val sideEffect: SharedFlow<ReportSideEffect>
        get() = _sideEffect

    init {
        val reportArgs = savedStateHandle.toRoute<Report>()
        _state.value = _state.value.copy(
            postId = UiState.Success(data = reportArgs.postId),
            userId = UiState.Success(data = reportArgs.userId)
        )
    }

    fun updateSelectedReportOption(newOption: ReportOption) {
        _state.update {
            it.copy(selectedReportOption = newOption)
        }
    }

    fun updateReportContext(newContext: String) {
        _state.update {
            it.copy(reportContext = newContext)
        }
        when (isValidLength(newContext)) {
            true -> _state.value = _state.value.copy(reportButtonEnabled = true)
            false -> _state.value = _state.value.copy(reportButtonEnabled = false)
        }
    }

    private fun isValidLength(input: String, minLength: Int = 1, maxLength: Int = 300): Boolean {
        return input.length in minLength..maxLength
    }

    fun reportPost(reportType: String, reportDetail: String) {
        val postId = _state.value.postId
        val userId = _state.value.userId
        when (postId is UiState.Success && userId is UiState.Success) {
            true -> {
                val postIdData = postId.data
                val userIdData = userId.data
                viewModelScope.launch {
                    reportRepository.postReportPost(postId = postIdData, userId = userIdData, reportType = reportType, reportDetail = reportDetail)
                        .onSuccess {
                            _sideEffect.emit(ReportSideEffect.ShowDialog)
                        }
                        .onFailure(Timber::e)
                }
            }
            false -> Timber.e("postId or userId is not exist")
        }
    }
}
