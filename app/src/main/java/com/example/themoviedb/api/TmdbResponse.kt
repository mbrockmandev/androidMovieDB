package com.example.themoviedb.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbResponse(
    val movies: MovieResponse
)
