package com.spoony.spoony.core.state

enum class ErrorType(val description: String) {
    SERVER_CONNECTION_ERROR("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."),
    UNEXPECTED_ERROR("예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.")
}
