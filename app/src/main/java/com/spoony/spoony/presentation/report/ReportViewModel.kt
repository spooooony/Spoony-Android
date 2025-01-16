package com.spoony.spoony.presentation.report

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel() {
    var state: MutableStateFlow<ReportState> =
        MutableStateFlow(ReportState())
        private set
}
