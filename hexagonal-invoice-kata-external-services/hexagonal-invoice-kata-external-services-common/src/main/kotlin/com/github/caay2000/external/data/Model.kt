package com.github.caay2000.external.model

import java.math.BigDecimal
import java.time.LocalDate

data class ProductData(
    val id: String,
    val name: String,
    val price: Int,
    val discountedPrice: BigDecimal
)

data class AccountData(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String,
    val birthDate: LocalDate,
    val premiumAccount: Boolean = id.toInt() % 2 == 0 || id.toInt() % 3 == 1,
    val gender: Gender = Gender.values()[id.toInt() % 3]
)

enum class Gender {
    MALE,
    FEMALE,
    UNDEFINED
}
