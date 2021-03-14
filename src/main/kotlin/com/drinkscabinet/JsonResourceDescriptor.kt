package com.drinkscabinet

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.squareup.moshi.Moshi

import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URI
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

data class JsonResourceDescriptor(
    val subject: String,
    val expires : Date? = null,
    val aliases : MutableList<String> = mutableListOf(),
    val properties : MutableMap<URI, String> = mutableMapOf(),
    val links : MutableList<Link> = mutableListOf())

data class Link(
    val rel : String,
    val type : String?,
    val href : URI?,
    val titles : Map<String, String>? = null,
    val properties : Map<String, String>? = null
)

class UriConverter : Converter {
    override fun canConvert(cls: Class<*>): Boolean {
        return cls == URI::class.java
    }

    override fun fromJson(jv: JsonValue): Any? {
        return URI.create(jv.string!!)
    }

    override fun toJson(value: Any): String {
        return value.toString()
    }
}

class DateConverter : Converter {
    override fun canConvert(cls: Class<*>): Boolean {
        return cls == Date::class.java
    }

    override fun fromJson(jv: JsonValue): Any? {
        return SimpleDateFormat.getDateTimeInstance().parse(jv.string!!)
    }

    override fun toJson(value: Any): String {
        return SimpleDateFormat.getDateInstance().format(value)
    }
}


private fun main() {
    val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(UriAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(JsonResourceDescriptor::class.java).indent("  ")

    val link1 = Link("myRel", "myType", URI.create("http://myHref"))
    val resource = JsonResourceDescriptor("myResource", Date(Instant.now().toEpochMilli()))
    resource.links.add(link1)
    println(adapter.toJson(resource))

    val parsed = adapter.fromJson(testInput2)
    println(parsed)
    println("Klaxon")
    println(Klaxon().converter(UriConverter()).converter(DateConverter()).toJsonString(resource))
}

private fun main2() {
}


private val testInput = """
    {
      "subject" : "acct:paulej@packetizer.com",
      "properties" :
      {
        "http://packetizer.com/ns/name" : "Paul E. Jones"
      },
      "links" :
      [
        {
          "rel" : "http://webfinger.net/rel/profile-page",
          "href" : "http://www.packetizer.com/people/paulej/"
        },
        {
          "rel" : "http://packetizer.com/rel/blog",
          "type" : "text/html",
          "href" : "http://www.packetizer.com/people/paulej/blog/",
          "titles" :
          {
            "en-us" : "Paul E. Jones' Blog"
          }
        }
      ]
    }
""".trimIndent()

private val testInput2 = """
    
    {
      "subject" : "acct:bob@example.com",
      "aliases" :
      [
        "http://www.example.com/~bob/"
      ],
      "properties" :
      {
        "http://packetizer.com/ns/name" : "Bob Q. Smith"
      },
      "links" :
      [
        {
          "rel" : "http://webfinger.net/rel/avatar",
          "type" : "image/jpeg",
          "href" : "http://www.example.com/~bob/bob.jpg"
        },
        {
          "rel" : "http://packetizer.com/rel/businesscard",
          "type" : "text/vcard",
          "href" : "http://www.example.com/~bob/bob.vcf"
        },
        {
          "rel" : "http://webfinger.net/rel/profile-page",
          "href" : "http://www.example.com/~bob/"
        },
        {
          "rel" : "http://packetizer.com/rel/blog",
          "type" : "text/html",
          "href" : "http://blogs.example.com/bob/",
          "titles" :
          {
            "en-us" : "The Magical World of Bob",
            "fr" : "Le Monde Magique de Bob"
          }
        },
        {
          "rel" : "http://packetizer.com/rel/smtp-server",
          "properties" :
          {
            "http://packetizer.com/ns/host" : "smtp.example.com",
            "http://packetizer.com/ns/port" : "587",
            "http://packetizer.com/ns/login-required" : "yes",
            "http://packetizer.com/ns/transport" : "starttls"
          }
        },
        {
          "rel" : "http://packetizer.com/rel/imap-server",
          "properties" :
          {
            "http://packetizer.com/ns/host" : "imap.example.com",
            "http://packetizer.com/ns/port" : "993",
            "http://packetizer.com/ns/transport" : "ssl"
          }
        }
      ]
    }
""".trimIndent()