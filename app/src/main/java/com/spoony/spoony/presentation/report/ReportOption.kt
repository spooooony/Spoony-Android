package com.spoony.spoony.presentation.report

enum class ReportOption(val displayName: String) {
    COMMERCIAL("영리 목적/ 홍보성 후기"),
    ABUSIVE("욕설/ 인신 공격"),
    ILLEGAL("불법정보"),
    PERSONAL_INFO("개인 정보 노출"),
    SPAM("도배"),
    OTHER("기타")
}
