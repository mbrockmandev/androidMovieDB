package com.example.themoviedb

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.api.MovieItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "MainMovieViewModel"

class MainMovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()
    private val prefsRepository = PreferencesRepository.get()

    private val _uiState: MutableStateFlow<MovieListUIState> = MutableStateFlow(
        MovieListUIState()
    )
    val uiState: StateFlow<MovieListUIState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            prefsRepository.storedQuery.collectLatest { storedQuery ->
                try {
                    val items = fetchListItems(storedQuery)
                    _uiState.update { oldState ->
                        oldState.copy(
                            movies = items,
                            query = storedQuery
                        )
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to fetch gallery items", ex)
                }
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch { prefsRepository.setStoredQuery(query) }
    }

    private suspend fun fetchListItems(query: String): List<MovieItem> {
        return if (query.isNotEmpty()) {
            movieRepository.searchMovies(query)
        } else {
            movieRepository.fetchMovies()
        }
    }
}

data class MovieListUIState(
    val movies: List<MovieItem> = listOf(),
    val query: String = "",
)