package com.github.caay2000.application.domain

import com.github.caay2000.application.model.common.AccountId
import com.github.caay2000.application.model.inbound.AccountApi
import com.github.caay2000.application.model.inbound.ProductApi
import com.github.caay2000.application.model.outbound.Account
import com.github.caay2000.application.model.outbound.Invoice
import com.github.caay2000.application.model.outbound.InvoiceApi
import com.github.caay2000.application.model.outbound.Product

class InvoiceService(
    private val accountApi: AccountApi,
    private val productApi: ProductApi
) : InvoiceApi {

    override fun getInvoice(accountId: AccountId): Invoice {
        val account = accountApi.getAccount(accountId)
        val products = retrieveProducts(accountId, account.premium)

        return Invoice(
            account = Account(
                id = account.accountId,
                name = account.name,
                address = account.address,
                city = account.city,
                postalCode = account.postalCode,
                email = account.email
            ),
            products = products,
            totalAmount = getTotalAmount(products)
        )
    }

    private fun retrieveProducts(accountId: AccountId, premium: Boolean): List<Product> =
        productApi.getProducts(accountId)
            .filter { it.isActive() }
            .map {
                Product(
                    id = it.productId,
                    name = it.name,
                    price = if (premium) it.premiumPrice else it.price
                )
            }

    private fun getTotalAmount(products: List<Product>) = products.sumOf { it.price }
}
