package com.drinkscabinet.tfl

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.Duration

/**
 * Command-line application to check next trains at Deptford Bridge DLR station using TFL Unified API
 */
fun main(args: Array<String>) {
    val apiKey = System.getenv("TFL_API_KEY") ?: "e422dd6a9dc049beb5c8ab104701fdc7"
    
    if (apiKey.isEmpty()) {
        println("Warning: TFL_API_KEY environment variable not set. API may be rate-limited.")
        println("Set it using: export TFL_API_KEY=your_api_key_here")
        println()
    }
    
    val checker = TflTrainChecker(apiKey)
    checker.getNextTrains()
}

class TflTrainChecker(private val apiKey: String) {
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build()
    
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    
    // Deptford Bridge DLR station NaPTAN code
    private val stationId = "940GZZDLDEP"
    
    fun getNextTrains() {
        try {
            println("Fetching next trains at Deptford Bridge DLR station...")
            println("=" .repeat(60))
            
            val arrivals = fetchArrivals()
            
            if (arrivals.isEmpty()) {
                println("No trains currently scheduled.")
                return
            }
            
            // Sort by expected arrival time
            val sortedArrivals = arrivals.sortedBy { it.timeToStation }
            
            println("\nNext ${sortedArrivals.size} trains:")
            println("-".repeat(60))
            
            sortedArrivals.forEach { arrival ->
                val minutes = arrival.timeToStation / 60
                val destination = arrival.destinationName
                val platform = arrival.platformName ?: "N/A"
                val towards = arrival.towards ?: ""
                
                println("→ ${destination}")
                println("  Platform: ${platform}")
                if (towards.isNotEmpty()) {
                    println("  Towards: ${towards}")
                }
                println("  Arriving in: ${minutes} min (${arrival.timeToStation}s)")
                println()
            }
            
        } catch (e: Exception) {
            println("Error fetching train data: ${e.message}")
            e.printStackTrace()
        }
    }
    
    private fun fetchArrivals(): List<Arrival> {
        val url = buildUrl()
        
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()
        
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        
        if (response.statusCode() != 200) {
            throw RuntimeException("API request failed with status ${response.statusCode()}: ${response.body()}")
        }
        
        return objectMapper.readValue(response.body())
    }
    
    private fun buildUrl(): String {
        val baseUrl = "https://api.tfl.gov.uk/StopPoint/${stationId}/Arrivals"
        return if (apiKey.isNotEmpty()) {
            "${baseUrl}?app_key=${apiKey}"
        } else {
            baseUrl
        }
    }
}

/**
 * Data class representing a train arrival
 */
data class Arrival(
    val id: String,
    val stationName: String,
    val platformName: String?,
    val destinationName: String,
    val towards: String?,
    val timeToStation: Int,
    val expectedArrival: String,
    val lineName: String,
    val lineId: String
)
