package com.clone.coupangclone.common.config.rx

import com.clone.coupangclone.common.extension.isNullOrEmptyOrBlank
import io.reactivex.rxjava3.core.Observable
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.context.request.async.WebAsyncUtils
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer

class ObservableReturnValueHandler : AsyncHandlerMethodReturnValueHandler {
    override fun isAsyncReturnValue(returnValue: Any?, returnType: MethodParameter): Boolean {
        return !returnValue.isNullOrEmptyOrBlank() && returnValue is Observable<*>
    }

    override fun supportsReturnType(returnType: MethodParameter): Boolean {
        return Observable::class.java.isAssignableFrom(returnType.parameterType)
    }

    @Throws(Exception::class)
    override fun handleReturnValue(
        returnValue: Any?,
        returnType: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest,
    ) {
        if (returnValue.isNullOrEmptyOrBlank()) {
            mavContainer.isRequestHandled = true
            return
        }
        val observable = Observable::class.java.cast(returnValue)
        WebAsyncUtils.getAsyncManager(webRequest)
            .startDeferredResultProcessing(ObservableAdapter(observable), mavContainer)
    }

    class ObservableAdapter<T>(observable: Observable<T>) : DeferredResult<T>(TimeoutConfig.DEFAULT_TIMEOUT) {
        init {
            observable.subscribe({ result -> setResult(result) }) { result ->
                setErrorResult(result)
            }
        }
    }
}
