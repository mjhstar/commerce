package com.clone.coupangclone.common.web.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {
    INVALID_EMAIL(HttpStatus.BAD_REQUEST,"USER-1", "email 형식이 일치하지 않습니다."),



    ;
}
