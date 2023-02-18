package com.clone.commerce.product.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product/customer")
class CustomerProductController(
) {
    companion object{
        private const val FIND_PRODUCT = "/v1/product"
    }
}
