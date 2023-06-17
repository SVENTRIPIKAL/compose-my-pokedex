package com.example.pokedexapp.ui

import androidx.compose.runtime.Composable
import com.example.pokedexapp.ui.screens.ScreenNavigator


@Composable
fun MainPokedexApp(viewModel: MainViewModel) {

    ScreenNavigator(viewModel.uiState, viewModel)
}