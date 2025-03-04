package org.example.kmpmovies.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.back
import org.example.kmpmovies.data.Movie
import org.example.kmpmovies.ui.common.LoadingIndicator
import org.example.kmpmovies.ui.screens.Screen
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier: Modifier = Modifier, viewModel: DetailViewModel, onBack: () -> Unit) {
    val state = viewModel.state

    Screen {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(state.movie?.title ?: "")
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(Res.string.back)
                    )
                }
            })
        }) { padding ->
            LoadingIndicator(enabled = state.loading)

            state.movie?.let { movie ->
                Column(
                    modifier = modifier.padding(padding).verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = movie.poster,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.padding(horizontal = 8.dp).fillMaxWidth()
                            .aspectRatio(16f / 9f).clip(MaterialTheme.shapes.medium)
                    )
                    Text(
                        text = movie.title,
                        modifier = modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}