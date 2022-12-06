package com.clone.coupangclone.common.web.exception

import com.clone.coupangclone.common.logging.LoggingCompanion
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler(

) {
    companion object : LoggingCompanion()

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse>{
        logger.error("Error :::", e)
        TODO()
    }
}