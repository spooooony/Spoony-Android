package com.spoony.spoony.presentation.report.type

enum class ReportOption(
    val displayName: String,
    val code: String
) {
    ADVERTISEMENT("영리 목적/ 홍보성 후기", "ADVERTISEMENT"),
    INSULT("욕설/ 인신 공격", "INSULT"),
    ILLEGAL_INFO("불법정보", "ILLEGAL_INFO"),
    PERSONAL_INFO("개인 정보 노출", "PERSONAL_INFO"),
    DUPLICATE("도배", "DUPLICATE"),
    OTHER("기타", "OTHER")
}
