package com.drinkscabinet

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URI

class UriAdapter {
    @ToJson
    fun toJson(uri: URI) : String {
        return uri.toASCIIString()
    }

    @FromJson
    fun fromJson(s: String) : URI {
        return URI.create(s)
    }
}