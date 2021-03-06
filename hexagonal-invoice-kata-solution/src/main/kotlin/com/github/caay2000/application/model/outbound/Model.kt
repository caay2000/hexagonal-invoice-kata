package com.github.caay2000.application.model.outbound

import com.github.caay2000.application.model.common.AccountId
import com.github.caay2000.application.model.common.ProductId
import java.math.BigDecimal

data class Invoice(
    val account: Account,
    val products: List<Product>,
    val totalAmount: BigDecimal
)

data class Product(
    val id: ProductId,
    val name: String,
    val price: BigDecimal
)

data class Account(
    val id: AccountId,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String
)
