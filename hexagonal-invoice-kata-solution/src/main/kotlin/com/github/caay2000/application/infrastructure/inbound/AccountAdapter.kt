package com.github.caay2000.application.infrastructure.inbound

import com.github.caay2000.application.model.common.AccountId
import com.github.caay2000.application.model.inbound.Account
import com.github.caay2000.application.model.inbound.AccountApi
import com.github.caay2000.external.client.AccountClient

class AccountAdapter(private val accountClient: AccountClient) : AccountApi {

    override fun getAccount(accountId: AccountId): Account {
        val account = accountClient.getAccountById(accountId)
        val premium = accountClient.getPremiumFeatures(accountId)

        return Account(
            accountId = accountId,
            name = account.name,
            address = account.address,
            city = account.city,
            postalCode = account.postalCode,
            email = account.email,
            premium = premium
        )
    }
}
