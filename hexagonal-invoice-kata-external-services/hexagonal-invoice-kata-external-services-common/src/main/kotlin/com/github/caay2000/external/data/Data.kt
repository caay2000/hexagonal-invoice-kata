package com.github.caay2000.external.data

import com.github.caay2000.external.model.AccountData
import com.github.caay2000.external.model.ProductData
import java.math.BigDecimal
import java.time.LocalDate

object Data {

    val productData = mapOf(
        Pair("100", ProductData("100", "TV", 80, BigDecimal(69.95))),
        Pair("120", ProductData("120", "TV PREMIUM", 120, BigDecimal(99.95))),
        Pair("200", ProductData("200", "LAND LINE", 20, BigDecimal(18.50))),
        Pair("300", ProductData("300", "PHONE", 60, BigDecimal(49.95))),
        Pair("400", ProductData("400", "INTERNET (20MB)", 40, BigDecimal(29.95))),
        Pair("410", ProductData("410", "INTERNET (300MB)", 60, BigDecimal(49.95))),
        Pair("420", ProductData("420", "INTERNET (1GB)", 80, BigDecimal(42.50)))
    )

    val accountData = mapOf(
        Pair("8069311", AccountData("8069311", "Uriel Torres", "P.O. Box 853, 5399 Dolor Av.", "San Pietro", "701410", "eget@odio.ca", "08/07/1971".toLocalDate())),
        Pair("8267017", AccountData("8267017", "Ivory Ayers", "P.O. Box 497, 148 Morbi Road", "Aspuket", "86319", "a.magna@necleo.net", "02/11/1968".toLocalDate())),
        Pair("8301466", AccountData("8301466", "Christen Conrad", "734-6825 Nunc Av.", "Satriano di Lucania", "875760", "purus@tempusmauris.co.uk", "11/18/1952".toLocalDate())),
        Pair("8461913", AccountData("8461913", "Constance Middleton", "P.O. Box 139, 381 Dis Rd.", "Aosta", "73760-79194", "consectetuer.ipsum.nunc@adipiscingelitCurabitur.co.uk", "12/30/1971".toLocalDate())),
        Pair("8535077", AccountData("8535077", "Baxter Combs", "Ap #641-4523 Quisque Av.", "Jasper", "BK8M 0OL", "et@enim.org", "07/21/1959".toLocalDate())),
        Pair("8651711", AccountData("8651711", "Gareth Brady", "1123 Metus Rd.", "Värnamo", "24573", "amet@Aliquam.edu", "05/15/1957".toLocalDate())),
        Pair("8729755", AccountData("8729755", "Silas Mack", "Ap #891-8718 Vitae Ave", "Bayreuth", "98684", "amet@mollisDuissit.ca", "09/07/1991".toLocalDate())),
        Pair("8740957", AccountData("8740957", "Hikel Lopez", "452-500 Eu Rd.", "Geleen", "31390", "quam.elementum.at@sedleo.ca", "07/16/1985".toLocalDate())),
        Pair("8832953", AccountData("8832953", "Carla Casey", "P.O. Box 930, 3622 Eu St.", "Sterling Heights", "49376", "Aenean@nunc.edu", "11/19/1988".toLocalDate()))
    )

    private val date = LocalDate.now()

    val accountProductData = mapOf<String, Map<String, Pair<LocalDate, LocalDate?>>>(
        Pair(
            "8069311", mapOf(
                Pair("200", Pair(date.minusMonths(12), null)),
                Pair("300", Pair(date.minusMonths(12), null))
            )
        ),
        Pair(
            "8267017", mapOf(
                Pair("120", Pair(date.minusMonths(12), null)),
                Pair("200", Pair(date.minusMonths(12), null)),
                Pair("300", Pair(date.minusMonths(12), null)),
                Pair("420", Pair(date.minusMonths(12), null))
            )
        ),
        Pair(
            "8301466", mapOf(
                Pair("100", Pair(date.minusMonths(12), null)),
                Pair("200", Pair(date.minusMonths(12), null)),
                Pair("300", Pair(date.minusMonths(12), date.minusMonths(10))),
                Pair("410", Pair(date.minusMonths(12), null))
            )
        ),
        Pair(
            "8461913", mapOf(
                Pair("100", Pair(date.minusMonths(12), date.minusMonths(10))),
                Pair("200", Pair(date.minusMonths(12), null)),
                Pair("300", Pair(date.minusMonths(12), null))
            )
        ),
        Pair("8535077", mapOf()),
        Pair(
            "8651711", mapOf(
                Pair("100", Pair(date.minusMonths(12), null)),
                Pair("200", Pair(date.minusMonths(12), null))
            )
        ),
        Pair(
            "8729755", mapOf(
                Pair("300", Pair(date.minusMonths(12), null)),
                Pair("400", Pair(date.minusMonths(12), null))
            )
        ),
        Pair(
            "8740957", mapOf(
                Pair("120", Pair(date.minusMonths(12), date.minusMonths(10)))
            )
        ),
        Pair(
            "8832953", mapOf(
                Pair("120", Pair(date.minusMonths(12), null)),
                Pair("200", Pair(date.minusMonths(12), null)),
                Pair("300", Pair(date.minusMonths(12), null)),
                Pair("400", Pair(date.minusMonths(12), null))
            )
        )
    )

    private fun String.toLocalDate(): LocalDate {
        val split = this.split("/").map { it.toInt() }
        return LocalDate.of(split[2], split[0], split[1])
    }
}
