package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.repo.ProductRepository

class InvoiceApplication(
    private val accountClient: AccountClient,
    private val productClient: ProductClient,
    private val productRepository: ProductRepository
) {

    fun getInvoiceByAccountId(accountId: String): Invoice {

        val account = accountClient.getAccountById(accountId)
        val listAccountProducts = productClient.getProductsByAccountId(accountId)
        val productInformation = productRepository.getProductInformation()

        val accountProducts = listAccountProducts.filter {
            it.value == null
        }.map {
            productInformation.first { data -> data.id == it.key }
        }

        return Invoice(
            account = account,
            products = accountProducts,
            totalAmount = accountProducts.map { it.price }.sum()
        )
    }

    data class Invoice(
        val account: Account,
        val products: List<Product>,
        val totalAmount: Int
    )
}
