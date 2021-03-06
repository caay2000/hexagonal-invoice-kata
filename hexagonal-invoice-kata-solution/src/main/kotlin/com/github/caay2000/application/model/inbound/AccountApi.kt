package com.github.caay2000.application.model.inbound

import com.github.caay2000.application.model.common.AccountId

interface AccountApi {
    fun getAccount(accountId: AccountId): Account
}
