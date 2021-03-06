package com.github.caay2000.external.repo

import com.github.caay2000.external.data.Data.productData
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.model.ProductRepositoryConfiguration

@Suppress("UNUSED_PARAMETER")
class ProductRepository(configuration: ProductRepositoryConfiguration) {

    fun getProductInformation(): List<Product> =
        productData.map {
            Product(
                id = it.value.id,
                productName = it.value.name,
                productPrice = it.value.price
            )
        }
}
