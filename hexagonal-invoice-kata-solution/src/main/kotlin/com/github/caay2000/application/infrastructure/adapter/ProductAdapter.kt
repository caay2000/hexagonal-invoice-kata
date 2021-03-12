package com.github.caay2000.application.infrastructure.adapter

import com.github.caay2000.application.model.common.AccountId
import com.github.caay2000.application.model.inbound.Product
import com.github.caay2000.application.model.inbound.ProductApi
import com.github.caay2000.external.client.ProductClient

class ProductAdapter(
    private val productClient: ProductClient
) : ProductApi {

    override fun getProducts(accountId: AccountId): List<Product> {
        val products = productClient.getProductsByAccountId(accountId)

        return products.map { product ->
            Product(
                id = product.id,
                name = product.name,
                price = product.price,
                premiumPrice = product.premiumPrice,
                endDate = product.endDate
            )
        }
    }
}