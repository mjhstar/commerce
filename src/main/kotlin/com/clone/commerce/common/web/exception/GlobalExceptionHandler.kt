package com.clone.commerce.common.web.exception

import com.clone.commerce.common.logging.LoggingCompanion
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.status
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler(
) {
    companion object : LoggingCompanion()

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Error occurred", e)
        return badRequest().body(
            ErrorResponse(
                message = "Argument type mismatch. ${e.message}",
                path = request.requestURI,
                serviceName = "commerce",
                errorCode = "ArgumentTypeMismatchException"
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Error occurred", e)
        return badRequest().body(
            ErrorResponse(
                message = "Http request body message not readable. ${e.message}",
                path = request.requestURI,
                serviceName = "commerce",
                errorCode = "HttpMessageNotReadableException"
            )
        )
    }

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(
        e: BusinessException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = e.message,
            path = request.requestURI,
            serviceName = "commerce",
            errorCode = e.code
        )
        logger.error(
            """
            
            ==================Error occurred=====================
            errorCode : ${errorResponse.errorCode}
            message   : ${errorResponse.message}
            path      : ${errorResponse.path}
            =====================================================
            """.trimIndent()
        )
        return status(e.httpStatus).body(errorResponse)
    }
}
