package com.spoony.spoony.presentation.report

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

data class ReportState(
    val options: ImmutableList<String> = immutableListOf("영리 목적/ 홍보성 후기", "욕설/ 인신 공격", "불법정보", "개인 정보 노출", "도배", "기타"),
    val selectedOption: String = options[0],
    val reportContext: String = ""
)
