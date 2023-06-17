package com.example.pokedexapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedexapp.ui.MainPokedexApp
import com.example.pokedexapp.ui.MainViewModel
import com.example.pokedexapp.ui.theme.PokedexAppTheme
import com.example.pokedexapp.ui.theme.PokemonRed
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    val darkTheme = isSystemInDarkTheme()
                    SideEffect {
                        val statusBarColor = when (darkTheme){
                            true -> PokemonRed       // PokeTypeGHOST
                            false -> PokemonRed
                        }
                        systemUiController.setStatusBarColor(
                            color = statusBarColor,
                            darkIcons = false       // darkTheme
                        )
                    }


                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {

                        val viewModel: MainViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )

                        // MAIN APP HERE
                        MainPokedexApp(viewModel)
                    }
                }
            }
        }
    }
}


/**
 * ViewModel Factory class used to pass
 * the Main application context as a
 * parameter for the use of Database
 * creation inside the ViewModel.
 */
class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
