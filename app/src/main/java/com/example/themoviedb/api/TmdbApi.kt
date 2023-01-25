package com.example.themoviedb.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("")
    suspend fun fetchMovies(): TmdbResponse

    @GET("")
    suspend fun searchMovies(@Query("text") query: String): TmdbResponse
}