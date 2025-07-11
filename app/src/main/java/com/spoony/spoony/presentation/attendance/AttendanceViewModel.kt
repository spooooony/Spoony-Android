package com.spoony.spoony.presentation.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.core.util.extension.toHyphenDate
import com.spoony.spoony.domain.repository.SpoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
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
import timber.log.Timber

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val spoonRepository: SpoonRepository
) : ViewModel() {
    private val _state: MutableStateFlow<AttendanceState> = MutableStateFlow(AttendanceState())
    val state: StateFlow<AttendanceState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<AttendanceSideEffect>()
    val sideEffect: SharedFlow<AttendanceSideEffect>
        get() = _sideEffect.asSharedFlow()

    private var _showSpoonDraw: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showSpoonDraw: StateFlow<Boolean>
        get() = _showSpoonDraw.asStateFlow()

    val today: LocalDate = LocalDate.now(ZoneId.of("Asia/Seoul"))

    init {
        _state.update {
            it.copy(
                weeklyStartDate = today.with(DayOfWeek.MONDAY).toHyphenDate()
            )
        }

        checkSpoonDrawn(today)
    }

    fun getWeeklySpoonDraw() {
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
                            spoonDrawList = UiState.Failure(ErrorType.SERVER_CONNECTION_ERROR.description)
                        )
                    }
                    _sideEffect.emit(
                        AttendanceSideEffect.ShowSnackBar(ErrorType.SERVER_CONNECTION_ERROR.description)
                    )
                }
        }
    }

    suspend fun drawSpoon(): SpoonDrawModel {
        spoonRepository.drawSpoon().onSuccess { spoon ->
            spoonRepository.updateSpoonDrawn()

            with(spoon) {
                return SpoonDrawModel(
                    drawId = drawId,
                    spoonTypeId = spoonType.spoonTypeId,
                    spoonName = spoonType.spoonName,
                    spoonImage = spoonType.spoonGetImage,
                    spoonAmount = spoonType.spoonAmount,
                    localDate = localDate
                )
            }
        }.onLogFailure {
            _sideEffect.emit(
                AttendanceSideEffect.ShowSnackBar(ErrorType.SERVER_CONNECTION_ERROR.description)
            )
        }
        return SpoonDrawModel.DEFAULT
    }

    fun getWeeklyDate(startDate: String): String {
        try {
            val weekStartDate = LocalDate.parse(startDate, hyphenFormatter)
            val weekEndDate = weekStartDate.plusDays(6)

            return "${formatDateWithDayOfWeek(weekStartDate)} ~ ${formatDateWithDayOfWeek(weekEndDate)}"
        } catch (e: Exception) {
            Timber.e(e)
            viewModelScope.launch {
                _sideEffect.emit(
                    AttendanceSideEffect.ShowSnackBar(ErrorType.UNEXPECTED_ERROR.description)
                )
            }
            return ""
        }
    }

    private fun checkSpoonDrawn(today: LocalDate) {
        viewModelScope.launch {
            val (lastEntryDate, isSpoonDrawn) = spoonRepository.getSpoonDrawLog()

            val shouldShowSpoon = try {
                val parsedDate = LocalDate.parse(lastEntryDate)
                !(parsedDate.equals(today) && isSpoonDrawn)
            } catch (e: Exception) {
                true
            }

            _showSpoonDraw.update { shouldShowSpoon }
        }
    }

    fun updateShowSpoonDraw(showSpoonDraw: Boolean) {
        _showSpoonDraw.update { showSpoonDraw }
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
