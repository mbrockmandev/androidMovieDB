package com.example.themoviedb.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "movie") val movieItems: List<MovieItem>
)