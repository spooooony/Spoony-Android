package com.spoony.spoony.presentation.report.type

import com.spoony.spoony.presentation.report.ReportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class ReportOption(
    val text: String,
    val code: String
) {
    ADVERTISEMENT("영리 목적/ 홍보성 후기", "ADVERTISEMENT"),
    INSULT("욕설/ 인신 공격", "INSULT"),
    DEFAMATION_COPYRIGHT_INFRINGEMENT("명예 훼손 및 저작권 침해", "DEFAMATION_COPYRIGHT_INFRINGEMENT"),
    ILLEGAL_INFO("불법정보", "ILLEGAL_INFO"),
    PERSONAL_INFO("개인 정보 노출", "PERSONAL_INFO"),
    DUPLICATE("도배", "DUPLICATE"),
    OTHER("기타", "OTHER");
}
object ReportOptionSelector {
    fun getOptionsForType(reportType: ReportType): ImmutableList<ReportOption> {
        return when (reportType) {
            ReportType.POST -> persistentListOf(
                ReportOption.ADVERTISEMENT,
                ReportOption.INSULT,
                ReportOption.ILLEGAL_INFO,
                ReportOption.PERSONAL_INFO,
                ReportOption.DUPLICATE,
                ReportOption.OTHER
            )
            ReportType.USER -> persistentListOf(
                ReportOption.ADVERTISEMENT,
                ReportOption.INSULT,
                ReportOption.DEFAMATION_COPYRIGHT_INFRINGEMENT,
                ReportOption.DUPLICATE,
                ReportOption.OTHER
            )
        }
    }
}
