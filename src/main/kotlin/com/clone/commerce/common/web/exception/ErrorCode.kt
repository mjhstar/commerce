package com.clone.commerce.common.web.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "REQ-1", "유효하지 않은 요청입니다."),

    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "USER-1", "email 형식이 일치하지 않습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER-2", "이미 존재하는 email 입니다."),
    INVALID_USER_EMAIL(HttpStatus.BAD_REQUEST, "USER-3", "존재하지 않는 email 입니다."),
    INVALID_USER_PASSWORD(HttpStatus.BAD_REQUEST, "USER-4", "비밀번호가 일치하지 않습니다."),
    INVALID_USER_TOKEN(HttpStatus.OK, "USER-5", "로그인 정보가 유효하지 않습니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "USER-6", "존재하지 않는 회원정보입니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "USER-7", "입력한 두 개의 비밀번호가 일치하지 않습니다."),
    FAIL_CHANGE_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "USER-8", "비밀번호 변경을 실패하였습니다."),

    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "CATE-1", "카테고리가 이미 존재합니다."),
    NOT_EXIST_CATEGORY(HttpStatus.BAD_REQUEST, "CATE-2", "카테고리가 존재하지 않습니다."),
    FAIL_REGISTER_CATEGORY(HttpStatus.INTERNAL_SERVER_ERROR, "CATE-3", "카테고리 등록에 실패하였습니다."),
    FAIL_DELETE_CATEGORY(HttpStatus.BAD_REQUEST, "CATE-4", "하위 카테고리가 존재합니다."),


    MISMATCH_TOKEN_ISSUER(HttpStatus.BAD_REQUEST, "TOKEN-1", "정상적인 로그인이 아닙니다."),

    IS_MORE_THAN_HAS_POINT(HttpStatus.OK, "POINT-1", "보유한 적립금보다 많은 적립금을 사용할 수 없습니다."),
    FAIL_USE_POINT(HttpStatus.OK, "POINT-2", "적립금 사용에 실패하였습니다."),
    FAIL_CHARGE_POINT(HttpStatus.OK, "POINT-3", "포인트 적립에 실패하였습니다."),
    FAIL_CHARGE_POINT_ZERO(HttpStatus.OK, "POINT-4", "포인트 적립은 1원 이상만 가능합니다."),
    FAIL_USE_POINT_ZERO(HttpStatus.OK, "POINT-5", "포인트 사용은 1원 이상만 가능합니다."),
    FAIL_INVALID_POINT(HttpStatus.OK, "POINT-6", "포인트를 사용할 수 없습니다."),


    NOT_EXIST_TX(HttpStatus.OK, "TX-1", "포인트 사용 이력이 존재하지 않습니다."),
    FAIL_CANCEL_TX(HttpStatus.OK, "TX-2", "포인트 사용 취소에 실패하였습니다."),


    FAIL_CONVERT_DATE(HttpStatus.OK, "COM-1", "올바르지 않은 날짜 형식입니다."),
    TOO_LARGE_PAGE_SIZE(HttpStatus.OK, "COM-2", "조회할 수 있는 최대 페이지 사이즈는 100입니다."),
    FAIL_INQUIRY_DATE(HttpStatus.OK, "COM-3", "조회할 수 있는 최대 기간은 6개월입니다."),


    ;
}
