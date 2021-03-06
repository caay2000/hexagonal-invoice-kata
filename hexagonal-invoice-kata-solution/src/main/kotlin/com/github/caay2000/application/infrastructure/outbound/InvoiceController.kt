package com.github.caay2000.application.infrastructure.outbound

import com.github.caay2000.application.model.outbound.Account
import com.github.caay2000.application.model.outbound.InvoiceApi
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.math.BigDecimal
import java.math.RoundingMode

class InvoiceController(private val invoiceApi: InvoiceApi) {

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = invoiceApi.getInvoice(accountId)

        call.respond(
            InvoiceResponse(
                account = result.account,
                products = result.products.map {
                    ProductResponse(
                        id = it.id,
                        name = it.name,
                        price = it.price.round()
                    )
                },
                totalAmount = result.totalAmount.round()
            )
        )
    }

    data class InvoiceResponse(
        val account: Account,
        val products: List<ProductResponse>,
        val totalAmount: BigDecimal
    )

    data class ProductResponse(
        val id: String,
        val name: String,
        val price: BigDecimal
    )

    private fun BigDecimal.round(): BigDecimal = this.setScale(2, RoundingMode.HALF_UP)
}
