package com.example.themoviedb.api

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieItem(
    val title: String,
    val id: String,
    @Json(name = "url_s") val url: String,
    val owner: String // TODO: Need to look at JSON from TMDB and change this

) { // TODO: this is an argument to be passed to get detail view of movie
    // TODO: substitute with URI from my other movie db app
    val moviePageUri: Uri
        get() = Uri.parse("https://api.themoviedb.org/3/movie") // double check the uri
            .buildUpon()
            .appendPath(id)
            .build()
}

// get movie details
// https://api.themoviedb.org/3/movie/(id)?api_key=APIKEY&language=en-US
// return query results for query "onion"
// https://api.themoviedb.org/3/search/movie?api_key=____&language=en-US&query=onion&page=1