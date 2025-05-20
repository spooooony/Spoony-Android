package com.spoony.spoony.presentation.report.type

import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class ReportOption(
    val text: String,
    val code: String
) {
    // REPORT(USER)
    INSULT("욕설/ 인신 공격", "INSULT"),
    REPUTATION_AND_COPYRIGHT_VIOLATION("명예 훼손 및 저작권 침해", "REPUTATION_AND_COPYRIGHT_VIOLATION"),
    DUPLICATE("도배", "DUPLICATE"),

    // REPORT(POST)
    PROFANITY_OR_ATTACK("욕설/ 인신 공격", "PROFANITY_OR_ATTACK"),
    ILLEGAL_INFORMATION("불법정보", "ILLEGAL_INFORMATION"),
    PERSONAL_INFORMATION_EXPOSURE("개인 정보 노출", "PERSONAL_INFORMATION_EXPOSURE"),
    SPAM("도배", "SPAM"),

    PROMOTIONAL_CONTENT("영리 목적/ 홍보성 후기", "PROMOTIONAL_CONTENT"),
    OTHER("기타", "OTHER");
}
object ReportOptionSelector {
    fun getOptionsForType(reportType: ReportType): ImmutableList<ReportOption> {
        return when (reportType) {
            ReportType.POST -> persistentListOf(
                ReportOption.PROMOTIONAL_CONTENT,
                ReportOption.PROFANITY_OR_ATTACK,
                ReportOption.ILLEGAL_INFORMATION,
                ReportOption.PERSONAL_INFORMATION_EXPOSURE,
                ReportOption.SPAM,
                ReportOption.OTHER
            )
            ReportType.USER -> persistentListOf(
                ReportOption.PROMOTIONAL_CONTENT,
                ReportOption.INSULT,
                ReportOption.REPUTATION_AND_COPYRIGHT_VIOLATION,
                ReportOption.DUPLICATE,
                ReportOption.OTHER
            )
        }
    }
}
