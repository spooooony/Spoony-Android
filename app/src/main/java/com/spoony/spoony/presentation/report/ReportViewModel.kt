package com.spoony.spoony.presentation.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableStateFlow<ReportState> = MutableStateFlow(ReportState())
    val state: StateFlow<ReportState>
        get() = _state

    private val _sideEffect = MutableSharedFlow<ReportSideEffect>()
    val sideEffect: SharedFlow<ReportSideEffect>
        get() = _sideEffect

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
        val reportArgs = savedStateHandle.toRoute<Report>()
        val postId = reportArgs.postId
        val userId = reportArgs.userId
        when (postId > 0 && userId > 0) {
            true -> {
                viewModelScope.launch {
                    reportRepository.postReportPost(postId = postId, userId = userId, reportType = reportType, reportDetail = reportDetail)
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
