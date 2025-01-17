package com.spoony.spoony.presentation.report

enum class ReportOption(val displayName: String) {
    ADVERTISEMENT("영리 목적/ 홍보성 후기"),
    INSULT("욕설/ 인신 공격"),
    ILLEGAL_INFO("불법정보"),
    PERSONAL_INFO("개인 정보 노출"),
    DUPLICATE("도배"),
    OTHER("기타")
}
