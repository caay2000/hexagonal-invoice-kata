package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountData
import com.github.caay2000.external.data.Data.accountProductData
import com.github.caay2000.external.model.AccountProduct
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductClientException

@Suppress("UNUSED_PARAMETER")
class ProductClient(configuration: ProductClientConfiguration) {

    fun getProductsByAccountId(accountId: String): List<AccountProduct> {

        if (accountProductData.containsKey(accountId)) {
            return accountProductData[accountId]!!.map {
                AccountProduct(
                    id = it.key,
                    startDate = it.value.first,
                    endDate = it.value.second
                )
            }
        }
        throw ProductClientException()
    }
}
