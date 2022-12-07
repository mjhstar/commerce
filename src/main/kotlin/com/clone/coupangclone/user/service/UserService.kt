package com.clone.coupangclone.user.service

import com.clone.coupangclone.common.extension.isValidEmail
import com.clone.coupangclone.common.web.exception.BusinessException
import com.clone.coupangclone.common.web.exception.ErrorCode
import com.clone.coupangclone.user.entity.User
import com.clone.coupangclone.user.repository.UserRepository
import com.clone.coupangclone.user.request.SignUpRequest
import io.reactivex.rxjava3.core.Observable
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun signUp(request: SignUpRequest):Observable<User>{
        if(!request.email.isValidEmail()){
            throw BusinessException(ErrorCode.INVALID_EMAIL)
        }
        TODO()
    }

}
