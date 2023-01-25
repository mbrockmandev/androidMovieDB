package com.example.themoviedb.api

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieItem(
    val title: String,
    val id: String,
    @Json(name = "url_s") val url: String,
    val owner: String

) {
    val moviePageUri: Uri
        get() = Uri.parse("https://api.themoviedb.org/3/") // double check the uri
            .buildUpon()
            .appendPath(owner)
            .appendPath(id)
            .build()
}