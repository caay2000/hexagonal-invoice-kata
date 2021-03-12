package com.github.caay2000.application.domain

import com.github.caay2000.application.model.common.AccountId
import com.github.caay2000.application.model.inbound.AccountApi
import com.github.caay2000.application.model.inbound.ProductApi
import com.github.caay2000.application.model.outbound.Account
import com.github.caay2000.application.model.outbound.Invoice
import com.github.caay2000.application.model.outbound.InvoiceApi
import com.github.caay2000.application.model.outbound.Product
import java.math.BigDecimal
import java.math.RoundingMode

class InvoiceService(
    private val accountApi: AccountApi,
    private val productApi: ProductApi
) : InvoiceApi {

    override fun getInvoice(accountId: AccountId): Invoice {
        val account = accountApi.getAccount(accountId)
        val products = getProducts(accountId = accountId, premium = account.premium)

        return Invoice(
            account = Account(
                id = account.id,
                name = account.name,
                address = account.address,
                city = account.city,
                postalCode = account.postalCode,
                email = account.email
            ),
            products = products,
            totalAmount = products.sumOf { it.price }.setScale(2, RoundingMode.HALF_UP)
        )
    }

    private fun getProducts(accountId: AccountId, premium: Boolean): List<Product> =
        productApi.getProducts(accountId).filter {
            it.endDate == null
        }.map {
            Product(id = it.id, name = it.name, price = getPrice(it, premium))
        }

    private fun getPrice(product: com.github.caay2000.application.model.inbound.Product, premium: Boolean): BigDecimal {
        val price = if (premium) product.premiumPrice else product.price
        return price.setScale(2, RoundingMode.HALF_UP)
    }
}


