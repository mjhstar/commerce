package com.clone.commerce.common.web.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "REQ-1", "유효하지 않은 요청입니다."),

    INVALID_EMAIL(HttpStatus.BAD_REQUEST,"USER-1", "email 형식이 일치하지 않습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER-2", "이미 존재하는 email 입니다."),
    INVALID_USER_EMAIL(HttpStatus.BAD_REQUEST, "USER-3", "존재하지 않는 email 입니다."),
    INVALID_USER_PASSWORD(HttpStatus.BAD_REQUEST, "USER-4", "비밀번호가 일치하지 않습니다."),
    INVALID_USER_TOKEN(HttpStatus.BAD_REQUEST, "USER-5", "토큰이 유효하지 않습니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "USER-6", "존재하지 않는 회원정보입니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "USER-7", "입력한 두 개의 비밀번호가 일치하지 않습니다."),

    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "CATE-1", "카테고리가 이미 존재합니다."),
    NOT_EXIST_CATEGORY(HttpStatus.BAD_REQUEST, "CATE-2", "카테고리가 존재하지 않습니다."),



    MISMATCH_TOKEN_ISSUER(HttpStatus.BAD_REQUEST, "TOKEN-1", "토큰 발급자가 유효하지 않습니다."),






    ;
}
