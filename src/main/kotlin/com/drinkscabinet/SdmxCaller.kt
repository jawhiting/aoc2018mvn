package com.drinkscabinet

import it.bancaditalia.oss.sdmx.api.Dimension
import it.bancaditalia.oss.sdmx.client.SdmxClientHandler
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Flow(val provider: String, val id: String, val agency: String, val version: String, val description: String) {
    val dimensions = mutableListOf<Dimension>()

    fun getTimeseries(start: LocalDate, end: LocalDate, params: Map<String, String>) {
        // create the name from each code
        val query = dimensions.map { it.id }.map{ params.getOrDefault(it, "")}.joinToString(".")
        val complete = "$id.$query"
//        println(SdmxClientHandler.dumpTimeSeries(provider, complete, DateTimeFormatter.ISO_DATE.format(start), DateTimeFormatter.ISO_DATE.format(end)))
        val series = SdmxClientHandler.getTimeSeries(provider, complete, DateTimeFormatter.ISO_DATE.format(start), DateTimeFormatter.ISO_DATE.format(end))
        println(series)
    }
}

fun main() {

    val flows = SdmxClientHandler.getFlows("ECB", "EXR").map { (k, v) -> createFlow("ECB", k, v) }

    println(SdmxClientHandler.getDimensions("ECB", "EXR"))
    println(SdmxClientHandler.getCodes("ECB", "EXR", "CURRENCY"))

    flows[0].getTimeseries(LocalDate.of(2019, 11, 1), LocalDate.of(2020, 6, 1),
    mapOf("FREQ" to "Q", "CURRENCY" to "GBP", "CURRENCY_DENOM" to "EUR", "EXR_TYPE" to "SP00", "EXR_SUFFIX" to "A"))





//    var result = SdmxClientHandler.dumpTimeSeries("ECB", "EXR..GBP.EUR.SP00.A", null, null)
//    var ts = SdmxClientHandler.getTimeSeries("ECB", "EXR..GBP.EUR.SP00.A", null, null)

//    print(result)
}

fun createFlow(provider: String, key: String, value: String) : Flow {
    // key is agency,id,version
    println("Parsing $key -> $value")
    val s = key.split(',')
    val f = Flow(provider, s[1], s[0], s[2], value)

    val dim = SdmxClientHandler.getDimensions(f.provider, f.id).filterNotNull()
    f.dimensions.addAll(dim)
    println(f)
    f.getTimeseries(LocalDate.of(2019, 11, 1), LocalDate.now(), mapOf("FREQ" to "D", "CURRENCY" to "USD", "CURRENCY_DENOM" to "EUR", "EXR_TYPE" to "SP00", "EXR_SUFFIX" to "A"))
    return f
}