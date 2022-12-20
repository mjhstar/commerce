package com.clone.commerce.common.extension

import com.amazonaws.util.Base16
import com.amazonaws.util.Base64
import com.clone.commerce.common.config.security.Keys
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.lang3.RandomStringUtils

fun String.aesEncode(key: String = Keys.GENERAL_KEY, randomHeader: Boolean = true): String {
    return try {
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        c.init(Cipher.ENCRYPT_MODE, key.getKeySpec(), IvParameterSpec(key.substring(0, 16).toByteArray()))
        var input = ""
        input = if (randomHeader) {
            RandomStringUtils.randomAlphanumeric(5) + '\u000c' + this
        } else {
            this
        }
        val encrypted = c.doFinal(input.toByteArray(charset("UTF-8")))
        String(com.amazonaws.util.Base64.encode(encrypted))
    } catch (e: Exception) {
        this
    }
}

fun String.aesDecode(key: String = Keys.GENERAL_KEY): String {
    return try {
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        c.init(
            Cipher.DECRYPT_MODE,
            key.getKeySpec(),
            IvParameterSpec(key.substring(0, 16).toByteArray(charset("UTF-8")))
        )
        val byteStr = Base64.decode(this.toByteArray())
        val input = String(c.doFinal(byteStr), charset("UTF-8"))
        if (input.length > 5 && input[5] == '\u000c') {
            input.substring(6)
        } else {
            input
        }
    } catch (e1: Exception) {
        try {
            this.aesDecode16(key)
        } catch (e2: Exception) {
            this
        }
    }
}

fun String.aesDecode16(key: String): String {
    return try {
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        c.init(
            Cipher.DECRYPT_MODE,
            key.getKeySpec(),
            IvParameterSpec(key.substring(0, 16).toByteArray(charset("UTF-8")))
        )
        val byteStr = Base16.decode(this.toByteArray())
        val input = String(c.doFinal(byteStr), charset("UTF-8"))
        if (input.length > 5 && input[5] == '\u000c') {
            input.substring(6)
        } else {
            input
        }
    } catch (e: Exception) {
        this
    }
}

@Throws(Exception::class)
fun String.getKeySpec(): Key {
    val keyBytes = ByteArray(16)
    val b = this.substring(0, 16).toByteArray(charset("UTF-8"))
    var len = b.size
    if (len > keyBytes.size) {
        len = keyBytes.size
    }
    System.arraycopy(b, 0, keyBytes, 0, len)
    return SecretKeySpec(keyBytes, "AES")
}
