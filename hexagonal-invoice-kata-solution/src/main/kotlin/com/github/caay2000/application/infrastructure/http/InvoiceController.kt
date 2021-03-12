package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.model.outbound.InvoiceApi
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class InvoiceController(private val invoiceApi: InvoiceApi) {

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = invoiceApi.getInvoice(accountId)

        call.respond(result)
    }
}
