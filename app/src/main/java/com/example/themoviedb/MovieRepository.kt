package com.example.themoviedb

import com.example.themoviedb.api.MovieInterceptor
import com.example.themoviedb.api.MovieItem
import com.example.themoviedb.api.TmdbApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieRepository {
    private val tmdbApi: TmdbApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MovieInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()

        tmdbApi = retrofit.create()
    }

    suspend fun fetchMovies(): List<MovieItem> =
        tmdbApi.fetchMovies().movies.movieItems

    suspend fun searchMovies(query: String): List<MovieItem> =
        tmdbApi.searchMovies(query).movies.movieItems
}