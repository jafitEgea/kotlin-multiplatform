package org.example.kmpmovies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.kmpmovies.data.Movie
import org.example.kmpmovies.data.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: ViewModel(), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()



    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            moviesRepository.popularMovies.collect { movies ->
                if (movies.isNotEmpty()) {
                    _state.value = UiState(loading = false, movies = movies)
                }
            }
        }
    }

    data class UiState(
        val loading: Boolean = false, val movies: List<Movie> = emptyList()
    )
}
