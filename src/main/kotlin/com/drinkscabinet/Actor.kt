package com.drinkscabinet

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URI
import java.time.Instant
import java.util.*



data class Actor (
    override val id: URI,
    override val type: String,
    var following : URI? = null,
    var followers : URI? = null,
    var liked : URI? = null,
    var inbox : URI? = null,
    var outbox : URI? = null,
    var preferredUsername : String? = null,
    var name : String? = null,
    var summary : String? = null )
: ASObject()

private fun main() {
    val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(UriAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(Actor::class.java).indent("  ")

//    val link1 = Link("myRel", "myType", URI.create("http://myHref"))
//    val resource = JsonResourceDescriptor("myResource", Date(Instant.now().toEpochMilli()))
//    resource.links.add(link1)
//    println(adapter.toJson(resource))

    val parsed = adapter.fromJson(testInput)
    println("Parsed")
    println(parsed)

    println(adapter.toJson(parsed))
}

private val testInput = """
    {
      "@context": ["https://www.w3.org/ns/activitystreams",
                   {"@language": "ja"}],
      "type": "Person",
      "id": "https://kenzoishii.example.com/",
      "following": "https://kenzoishii.example.com/following.json",
      "followers": "https://kenzoishii.example.com/followers.json",
      "liked": "https://kenzoishii.example.com/liked.json",
      "inbox": "https://kenzoishii.example.com/inbox.json",
      "outbox": "https://kenzoishii.example.com/feed.json",
      "preferredUsername": "kenzoishii",
      "name": "石井健蔵",
      "summary": "この方はただの例です",
      "icon": [
        "https://kenzoishii.example.com/image/165987aklre4"
      ]
    }
""".trimIndent()


