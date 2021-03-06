package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.InvoiceApplication
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class InvoiceController(private val invoiceApplication: InvoiceApplication) {

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = invoiceApplication.getInvoiceByAccountId(accountId)

        call.respond(result)
    }
}
