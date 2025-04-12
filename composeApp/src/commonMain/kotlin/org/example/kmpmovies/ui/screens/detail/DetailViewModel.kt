package org.example.kmpmovies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.kmpmovies.data.Movie
import org.example.kmpmovies.data.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel(
    private val id: Int,
) : ViewModel(), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            moviesRepository.fetchMovieById(id).collect { movie ->
                movie?.let { _state.value = UiState(loading = false, movie = it) }
            }
        }
    }

    fun onFavoriteClick() {
        _state.value.movie?.let {
            viewModelScope.launch {
                moviesRepository.toggleFavorite(it)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
    )
}