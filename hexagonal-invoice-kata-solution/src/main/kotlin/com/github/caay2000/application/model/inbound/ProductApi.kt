package com.github.caay2000.application.model.inbound

import com.github.caay2000.application.model.common.AccountId

interface ProductApi {
    fun getProducts(accountId: AccountId): List<Product>
}