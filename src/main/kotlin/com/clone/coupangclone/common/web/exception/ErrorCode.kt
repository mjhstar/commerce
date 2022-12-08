package com.clone.coupangclone.common.web.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {
    INVALID_EMAIL(HttpStatus.BAD_REQUEST,"USER-1", "email 형식이 일치하지 않습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER-2", "이미 존재하는 email 입니다."),
    INVALID_USER_EMAIL(HttpStatus.BAD_REQUEST, "USER-3", "존재하지 않는 email 입니다."),
    INVALID_USER_PASSWORD(HttpStatus.BAD_REQUEST, "USER-4", "비밀번호가 일치하지 않습니다."),
    INVALID_USER_TOKEN(HttpStatus.BAD_REQUEST, "USER-5", "토큰이 유효하지 않습니다."),

    MISMATCH_TOKEN_ISSUER(HttpStatus.BAD_REQUEST, "TOKEN-1", "토큰 발급자가 유효하지 않습니다."),






    ;
}
