package com.clone.commerce.user.model.response

class ChangePwResponse(
    val email: String,
    val complete: Boolean
) {
    companion object {
        fun createBy(model: ChangePwResponseModel) = ChangePwResponse(
            email = model.email,
            complete = model.complete
        )
    }
}
