package com.github.caay2000.application.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.caay2000.application.domain.InvoiceApplication
import com.github.caay2000.application.infrastructure.http.InvoiceController
import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.AccountClientConfiguration
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductRepositoryConfiguration
import com.github.caay2000.external.repo.ProductRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get

@KtorExperimentalAPI
fun Application.main() {

    install(Koin) {
        modules(module {

            single { AccountClient(configuration = AccountClientConfiguration()) }
            single { ProductClient(configuration = ProductClientConfiguration()) }
            single { ProductRepository(configuration = ProductRepositoryConfiguration()) }

            single {
                InvoiceApplication(
                    accountClient = get(),
                    productClient = get(),
                    productRepository = get()
                )
            }
            single { InvoiceController(invoiceApplication = get()) }
        })
    }

    install(StatusPages) {
        exception<Throwable> { call.respond(HttpStatusCode.InternalServerError) }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
    }

    routing {
        get("/account/{accountId}/invoice", get<InvoiceController>().getInvoiceByAccountId())
    }
}
