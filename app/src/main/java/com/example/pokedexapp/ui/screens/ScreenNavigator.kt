package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedexapp.R
import com.example.pokedexapp.models.Pokemon
import com.example.pokedexapp.ui.MainViewModel
import com.example.pokedexapp.ui.UiState
import com.example.pokedexapp.ui.screens.bottomScreen.BottomScreen
import com.example.pokedexapp.ui.screens.topScreen.TopScreen
import com.example.pokedexapp.ui.theme.OutLined
import com.example.pokedexapp.ui.theme.PokemonRed
import com.example.pokedexapp.ui.theme.PokemonWhite


@Composable
fun ScreenNavigator(
    uiState: UiState,
    viewModel: MainViewModel
) {
    when (uiState) {
        is UiState.Error -> Error()
        is UiState.Success -> Success(uiState.pokemon, viewModel)
        is UiState.Preview -> {}    // SuccessPreview()
    }
}



// TOP SCAFFOLD +++ DONE
@Composable
fun InfoBar() {
    Column(
        modifier=Modifier.border(.5.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(PokemonRed)
        ) {

            Box(
                modifier = Modifier
                    .weight(.25f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.round_arrow_drop_down_24),
                        contentDescription = stringResource(R.string.arrow),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(R.string.info_top_bar),
                    color = PokemonWhite,
                    fontFamily = OutLined,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Box(
                modifier = Modifier
                    .weight(.35f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(R.string.english_top_bar),
                    color = PokemonWhite,
                    fontFamily = OutLined,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier
                .shadow(elevation = 10.dp)
        )
    }
}


@Composable
fun Error() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = stringResource(R.string.unexpected_error_please_restart_the_app),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


@Composable
fun Success(
    currentPokemon: Pokemon,
    viewModel: MainViewModel
) {

    Scaffold(
        topBar = { InfoBar() },
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .background(PokemonRed)
        ) {
            TopScreen(
                currentPokemon,
                viewModel.updateCompletionProgress(),
                viewModel.isComplete(),
                viewModel.typeColor,
                modifier=Modifier.weight(1f)
            )

            BottomScreen(
                viewModel = viewModel,
                currentPokemon = currentPokemon,
                downloadComplete = viewModel.isComplete(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}













///**
// * PREVIEW FUNCTION COMPOSABLE
// */
//@Composable
//fun PreviewSuccess(
//    currentPokemon: Pokemon,
//    viewModel: PreviewModel
//) {
//
//    Scaffold(
//        topBar = { InfoBar() },
//        bottomBar = {   }
//    ){
//        Column(
//            modifier = Modifier
//                .padding(it)
//                .background(PokemonRed)
//        ) {
//            TopScreen(
//                currentPokemon,
//                viewModel.updateCompletionProgress(),
//                viewModel.isComplete(),
//                viewModel.typeColor,
//                modifier=Modifier.weight(1f)
//            )
//
//            BottomScreen(
//                currentPokemon,
//                viewModel.isComplete(),
//                viewModel.pokemonList,
//                viewModel.currentAllPokemonLiveList,
//                viewModel.boxColor,
//                viewModel.updateCurrentPokemon,
//                modifier=Modifier.weight(1f)
//            )
//        }
//    }
//}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SuccessPreview() {
//    PokedexAppTheme {
//        val pokemon = getPokemon(FakeApiCharizard, FakeApiCharizardDesc)
//
//        val previewModel = PreviewModel()
//
//        PreviewSuccess(currentPokemon = pokemon, viewModel = previewModel)
//    }
//}