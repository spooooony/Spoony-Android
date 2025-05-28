package com.spoony.spoony.domain.usecase

import javax.inject.Inject

class FormatBirthDateUseCase @Inject constructor() {
    operator fun invoke(
        isBirthSelected: Boolean,
        year: String?,
        month: String?,
        day: String?
    ): String? {
        return if (isBirthSelected && year != null && month != null && day != null) {
            "$year-${month.padStart(2, '0')}-${day.padStart(2, '0')}"
        } else {
            null
        }
    }
}
