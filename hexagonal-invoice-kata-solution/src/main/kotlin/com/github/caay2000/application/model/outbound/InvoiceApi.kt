package com.github.caay2000.application.model.outbound

import com.github.caay2000.application.model.common.AccountId

interface InvoiceApi {
    fun getInvoice(accountId: AccountId): Invoice
}