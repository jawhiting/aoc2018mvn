package com.drinkscabinet

import java.net.URI

data class ASLink(
    val href : URI,
    val type : String = "Link",
    val hreflang : String? = null,
    val mediaType : String? = null,
    val name : String? = null
)

abstract class ASObject {
    abstract val id: URI
    abstract val type: String
}

private val testLink = """
    {
      "@context": "https://www.w3.org/ns/activitystreams",
      "type": "Link",
      "href": "http://example.org/abc",
      "hreflang": "en",
      "mediaType": "text/html",
      "name": "An example link"
    }
""".trimIndent()