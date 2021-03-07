package com.github.caay2000.external.model

import java.math.BigDecimal
import java.time.LocalDate

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val premiumPrice: BigDecimal
)

data class Account(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String
)

data class AccountProduct(
    val id: String,
    val startDate: LocalDate,
    val endDate: LocalDate?
)