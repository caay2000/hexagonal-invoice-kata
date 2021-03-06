package com.github.caay2000.external.model

import java.time.LocalDate

data class Product(
    val id: String,
    val productName: String,
    val productPrice: Int
)

data class Account(
    val accountId: String,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String
)