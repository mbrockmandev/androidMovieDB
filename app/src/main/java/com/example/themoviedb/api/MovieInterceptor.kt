package com.example.themoviedb.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MovieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        // TODO: This constructs URL for search requests and is used by
        // TODO: MovieRepository.kt
        // TODO: MODIFY THIS TO GET THE RIGHT RESULT!!!
        val newUrl: HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY.key)
            .addQueryParameter("language", "en-US")
            .addQueryParameter("page", "1")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

}

//https://api.themoviedb.org/3/movie/popular?api_key=${API_KEY.key}&language=en-US&page=1
/*
val newUrl: HttpUrl = originalRequest.url.newBuilder()
    .addQueryParameter("api_key", API_KEY.key)
    .addQueryParameter("format", "json")
    .addQueryParameter("nojsoncallback", "1")
    .addQueryParameter("extras", "url_s")
    .build()

 */