package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountProductData
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductClientException
import java.time.LocalDate

@Suppress("UNUSED_PARAMETER")
class ProductClient(configuration: ProductClientConfiguration) {

    fun getProductsByAccountId(accountId: String): Map<String, LocalDate?> {

        if (accountProductData.containsKey(accountId)) {
            return accountProductData[accountId]!!.mapValues {
                it.value.second
            }
        }
        throw ProductClientException()
    }
}
