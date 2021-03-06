package com.github.caay2000.external.model

import java.math.BigDecimal
import java.time.LocalDate

data class Product(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val premiumPrice: BigDecimal,
    val startDate: LocalDate,
    val endDate: LocalDate?
)

data class Account(
    val accountId: String,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String,
    val birthDate: LocalDate,
    val gender: Gender
)