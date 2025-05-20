package com.spoony.spoony.presentation.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.SpoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val spoonRepository: SpoonRepository
) : ViewModel() {
    private val _state: MutableStateFlow<AttendanceState> = MutableStateFlow(AttendanceState())
    val state: StateFlow<AttendanceState>
        get() = _state.asStateFlow()

    init {
        val today = LocalDate.now()
        _state.update {
            it.copy(
                weeklyStartDate = today.with(DayOfWeek.MONDAY).format(hyphenFormatter)
            )
        }

        getWeeklySpoonDraw()
    }

    private fun getWeeklySpoonDraw() {
        viewModelScope.launch {
            spoonRepository.getWeeklySpoonDraw()
                .onSuccess { weeklyDrawResult ->
                    _state.update {
                        it.copy(
                            spoonDrawList = UiState.Success(
                                weeklyDrawResult.spoonResultList.map { spoonEntity ->
                                    SpoonDrawModel(
                                        drawId = spoonEntity.drawId,
                                        spoonTypeId = spoonEntity.spoonType.spoonTypeId,
                                        spoonName = spoonEntity.spoonType.spoonName,
                                        spoonAmount = spoonEntity.spoonType.spoonAmount,
                                        spoonImage = spoonEntity.spoonType.spoonImage,
                                        localDate = spoonEntity.localDate
                                    )
                                }.toImmutableList()
                            ),
                            totalSpoonCount = weeklyDrawResult.totalSpoonCount
                        )
                    }
                }
                .onFailure { exception ->
                    Timber.e(exception)
                    _state.update {
                        it.copy(
                            spoonDrawList = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                        )
                    }
                }
        }
    }

    fun getWeeklyDate(startDate: String): String {
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
