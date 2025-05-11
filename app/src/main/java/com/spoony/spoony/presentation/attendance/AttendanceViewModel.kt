package com.spoony.spoony.presentation.attendance

import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

@HiltViewModel
class AttendanceViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<AttendanceState> = MutableStateFlow(AttendanceState())
    val state: StateFlow<AttendanceState>
        get() = _state.asStateFlow()

    init {
        getWeeklySpoonDraw()
    }

    private fun getWeeklySpoonDraw() {
        _state.update {
            it.copy(
                weeklyStartDate = getWeeklyDate("2025-05-05"),
                spoonDrawList = UiState.Success(
                    persistentListOf(
                        SpoonDrawModel(
                            drawId = 1,
                            spoonTypeId = 1,
                            spoonName = "일회용 티스푼",
                            spoonAmount = 1,
                            spoonImage = "",
                            localDate = "2025-05-05"
                        ),
                        SpoonDrawModel(
                            drawId = 2,
                            spoonTypeId = 2,
                            spoonName = "황금 스푼",
                            spoonAmount = 4,
                            spoonImage = "",
                            localDate = "2025-05-09"
                        )
                    )
                )
            )
        }
    }

    private fun getWeeklyDate(startDate: String): String {
        try {
            val weekStartDate = LocalDate.parse(startDate, hyphenFormatter)
            val weekEndDate = weekStartDate.plusDays(6)

            return "${formatDateWithDayOfWeek(weekStartDate)} ~ ${formatDateWithDayOfWeek(weekEndDate)}"
        } catch (e: Exception) {
            Timber.e(e)
            return ""
        }
    }

    private fun formatDateWithDayOfWeek(date: LocalDate): String {
        val dateString = date.format(dotFormatter)
        val dayOfWeekString = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        return "$dateString ($dayOfWeekString)"
    }

    companion object {
        val hyphenFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val dotFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}
