package com.github.caay2000.application.model.inbound

import com.github.caay2000.application.model.common.AccountId
import com.github.caay2000.application.model.common.ProductId
import java.math.BigDecimal
import java.time.LocalDate

data class Product(
    val id: ProductId,
    val name: String,
    val price: BigDecimal,
    val premiumPrice: BigDecimal,
    val endDate: LocalDate?
)

data class Account(
    val id: AccountId,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String,
    val premium: Boolean
)