package com.clone.commerce.common.web.exception

import com.clone.commerce.common.logging.LoggingCompanion
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

/**
 * determine error response except handling exceptions in [GlobalExceptionHandler]
 */
@Component
class CustomErrorAttributes(
) : DefaultErrorAttributes() {
    companion object : LoggingCompanion()

    override fun getErrorAttributes(webRequest: WebRequest?, options: ErrorAttributeOptions?): ErrorAttributes {
        val throwable = getError(webRequest)
        logger.error("Error occurred", throwable)

        val errorAttributes =
            super.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE))

        errorAttributes.apply {
            set("serviceName", "commerce")
            set("errorCode", "UnexpectedException")
            remove("status")
            remove("error")
        }
        return errorAttributes
    }
}

typealias ErrorAttributes = MutableMap<String, Any>