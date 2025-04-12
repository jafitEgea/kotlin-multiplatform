package org.example.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.kmpmovies.ui.screens.detail.DetailScreen
import org.example.kmpmovies.ui.screens.home.HomeScreen
import org.jetbrains.compose.resources.InternalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
//    val repository = rememberMoviesRepository(moviesDao)
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                // sin DI
//                viewModel = viewModel { HomeViewModel(repository)
//                }
            )
        }
        composable(
            route = "details/{id}",
            //Para definir el tipo de dato del argumento
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStrackEntry ->
            val movieId = checkNotNull(backStrackEntry.arguments?.getInt("id"))
            DetailScreen(
                viewModel = koinViewModel(parameters = { parametersOf(movieId) }),
                // sin DI
//                viewModel = viewModel { DetailViewModel(movieId, repository) },
                // se quita la pantalla actual de la pila
                onBack = { navController.popBackStack() }
            )
        }
    }
}

//@Composable
//private fun rememberMoviesRepository(moviesDao: MoviesDao): MoviesRepository = remember {
//
//    // Sin DI
//    val client = HttpClient {
//        install(ContentNegotiation) {
//            json(
//                Json {
//                    ignoreUnknownKeys = true
//                }
//            )
//        }
//        install(DefaultRequest) {
//            url {
//                protocol = URLProtocol.HTTPS
//                host = "api.themoviedb.org"
//                parameters.append("api_key", BuildConfig.API_KEY)
//            }
//        }
//    }
//    //Return
//    MoviesRepository(MoviesService(client), moviesDao)
//}