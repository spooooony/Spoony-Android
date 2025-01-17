package com.spoony.spoony.presentation.report

import androidx.lifecycle.ViewModel
import com.spoony.spoony.presentation.report.type.ReportOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel() {
    var _state: MutableStateFlow<ReportState> = MutableStateFlow(ReportState())
    val state: StateFlow<ReportState>
        get() = _state

    fun updateSelectedReportOption(newOption: ReportOption) {
        _state.value = _state.value.copy(selectedReportOption = newOption)
    }

    fun updateReportContext(newContext: String) {
        _state.value = _state.value.copy(reportContext = newContext)
    }
}
