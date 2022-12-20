package com.clone.commerce.common.extension

import java.util.Optional

fun Any?.isNullOrEmptyOrBlank():Boolean{
    if(this == null){
        return true
    }
    if(this is String && this.trim().isEmpty()){
        return true
    }
    if (this is Map<*, *> && this.isEmpty()) {
        return true
    }
    if (this is List<*> && this.isEmpty()) {
        return true
    }
    if (this is Optional<*> && !this.isPresent) {
        return true
    }
    if (this is Collection<*> && this.isEmpty()) {
        return true
    }
    return false
}
