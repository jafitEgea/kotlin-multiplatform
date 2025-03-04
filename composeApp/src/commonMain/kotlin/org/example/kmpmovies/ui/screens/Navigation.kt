package org.example.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.api_key
import kotlinx.serialization.json.Json
import org.example.kmpmovies.data.MoviesRepository
import org.example.kmpmovies.data.MoviesService
import org.example.kmpmovies.ui.screens.detail.DetailScreen
import org.example.kmpmovies.ui.screens.detail.DetailViewModel
import org.example.kmpmovies.ui.screens.home.HomeScreen
import org.example.kmpmovies.ui.screens.home.HomeViewModel
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(InternalResourceApi::class)
@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                viewModel = viewModel { HomeViewModel(repository) }
            )
        }
        composable(
            route = "details/{id}",
            //Para definir el tipo de dato del argumento
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStrackEntry ->
            val movieId = checkNotNull(backStrackEntry.arguments?.getInt("id"))
            DetailScreen(
                viewModel = viewModel { DetailViewModel(movieId, repository)},
                // se quita la pantalla actual de la pila
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    apiKey: String = stringResource(Res.string.api_key)
): MoviesRepository = remember {

    // Sin DI
    val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", apiKey)
            }
        }
    }
    //Return
    MoviesRepository(MoviesService(client))
}