package com.clone.coupangclone.common.extension

fun String?.isNullOrEmptyOrBlank(): Boolean{
    return this.isNullOrBlank() || this.isEmpty()
}
