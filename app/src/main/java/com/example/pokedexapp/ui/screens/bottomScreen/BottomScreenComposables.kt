package com.example.pokedexapp.ui.screens.bottomScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedexapp.MainActivity
import com.example.pokedexapp.R
import com.example.pokedexapp.models.Pokemon
import com.example.pokedexapp.ui.MainViewModel
import com.example.pokedexapp.ui.theme.PokeTypeFIGHTING
import com.example.pokedexapp.ui.theme.PokeTypeFLYING
import com.example.pokedexapp.ui.theme.PokeTypeGHOST
import com.example.pokedexapp.ui.theme.PokeTypeSTEEL
import com.example.pokedexapp.ui.theme.PokemonPink
import com.example.pokedexapp.ui.theme.PokemonWhite
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.system.exitProcess


@Composable
fun BottomScreen(
    viewModel: MainViewModel,
    currentPokemon: Pokemon,
    downloadComplete: Boolean,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.Black)
    ) {

        // CHECK IF BUILD COMPLETE
        if (downloadComplete) {         // #####    downloadComplete

            // SHOW GRID DATA
            ShowGrid(
                viewModel = viewModel,
                pokemonList = viewModel.getPokeList(),
                currentPokemon = currentPokemon,
                boxColor = viewModel.boxColor,
                updatePokemon = viewModel.updateCurrentPokemon,
                modifier = modifier
            )

        } else {

            // SHOW STILL BUILDING DATA
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PokemonWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.building_pokedex),
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowGrid(
    viewModel: MainViewModel,
    pokemonList: List<Pokemon>,
    currentPokemon: Pokemon,
    boxColor: (Pokemon) -> Color,
    updatePokemon: (Pokemon) -> Unit,
    modifier: Modifier
) {
    val onQuit = viewModel.onQuit
    val onSearch = viewModel.onSearch


    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(pokemonList) { pokemon ->
                    GridCellBox(pokemon, boxColor, updatePokemon)
                }
            },
            userScrollEnabled = true,
            flingBehavior = ScrollableDefaults.flingBehavior(),
            modifier = Modifier.weight(.84f)
        )

        if (onQuit) {
            AlertDialog(
                onDismissRequest = {}
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                    color = PokeTypeFLYING
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Close The App?",
                            fontWeight = FontWeight.SemiBold,
                            color = PokemonWhite
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.updateQuitStatus() },
                                colors = ButtonDefaults.buttonColors(PokemonWhite),
                                modifier = Modifier.width(100.dp),
                                border = BorderStroke(1.dp, Color.Black)
                            ) {
                                Text(
                                    text = "Return",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PokeTypeFIGHTING
                                )
                            }

                            Spacer(modifier = Modifier.width(25.dp))

                            OutlinedButton(
                                onClick = {
                                    MainActivity().finish()
                                    exitProcess(0)
                                },
                                colors = ButtonDefaults.buttonColors(PokeTypeFIGHTING),
                                modifier = Modifier.width(100.dp),
                                border = BorderStroke(1.dp, Color.Black)
                            ) {
                                Text(
                                    text = "Confirm",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PokemonWhite
                                )
                            }
                        }
                    }
                }
            }
        }

        if (onSearch) {

            var input by rememberSaveable { mutableStateOf("") }
            var list by rememberSaveable { mutableStateOf(pokemonList) }

            AlertDialog(
                onDismissRequest = { viewModel.updateSearchStatus() }
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(250.dp).wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                    color = PokeTypeFLYING
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = input,
                            onValueChange = {
                                input = it
                                list = viewModel.updateSearchList(it)
                            },
                            label = { Text(text = "Enter A Pokémon") },
                            placeholder = {
                                Text(text = currentPokemon.name
                                    .lowercase().replaceFirstChar { it.titlecase() }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(5),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.updateSearchStatus()
                                    viewModel.updateCurrentPokemonWithSearched(list)
                                }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = PokemonWhite,
                                unfocusedContainerColor = PokemonWhite,
                                focusedLabelColor = PokemonPink,
                                unfocusedLabelColor = PokeTypeSTEEL,
                                cursorColor = PokeTypeFIGHTING,
                                focusedTextColor = Color.Black,
                                focusedPlaceholderColor = PokeTypeSTEEL,
                                unfocusedPlaceholderColor = PokeTypeSTEEL
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            items(list) { pokemon ->
                                Text(
                                    text = pokemon.name
                                        .lowercase().replaceFirstChar { it.titlecase() },
                                    color = PokemonPink,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

            }
        }

        Row(
            modifier= Modifier
                .weight(.16f)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            val spacerWeight = .01f

            Spacer(modifier = Modifier.weight(spacerWeight))

            Row(
                modifier = Modifier
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .wrapContentHeight()
                    .weight(.98f)
                    .fillMaxWidth()
                    .background(PokeTypeGHOST),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /*TODO*/ },             // details Nav Host (Navigation Screen Update THAT)
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(PokemonWhite),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = stringResource(R.string.details),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { viewModel.updateSearchStatus() },                 // Search Bar Dialogue
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(PokemonWhite),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = stringResource(R.string.search),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { viewModel.updateQuitStatus() },       // Dialogue Box [Exit the app? Yes/No]
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(PokemonWhite),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = stringResource(R.string.quit),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(spacerWeight))
        }
    }
}



@Composable
fun GridCellBox(
    pokemon: Pokemon,
    boxColor: (Pokemon) -> Color,
    updatePokemon: (Pokemon) -> Unit
) {

    Column(modifier= Modifier
        .fillMaxSize()
        .border(width = 1.dp, color = boxColor(pokemon))
        .clickable(
            enabled = true,
            onClick = {
                updatePokemon(pokemon)
            },
        )
        .padding(3.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = pokemon.id,
                color = Color.Black,
                fontSize = 12.sp
            )
        }

        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(50.dp)
            ) {
                AsyncImage(                                   // *** REIMPLEMENT THIS
                    model = pokemon.sprites[1],
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                
//                Image(                                          // ONLY USED FOR PREVIEW
//                    painter = painterResource(id = R.drawable.charizard_small),
//                    contentDescription = pokemon.name,
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier.fillMaxSize()
//                )
            }
        }
    }
}