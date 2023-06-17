package com.example.pokedexapp.ui.screens.topScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedexapp.R
import com.example.pokedexapp.models.Pokemon
import com.example.pokedexapp.ui.theme.PokeTypeDARK
import com.example.pokedexapp.ui.theme.PokeTypeWATER
import com.example.pokedexapp.ui.theme.PokemonLightBlue
import com.example.pokedexapp.ui.theme.PokemonPink
import com.example.pokedexapp.ui.theme.PokemonRed
import com.example.pokedexapp.ui.theme.PokemonWhite


// TOP SCREEN +++ DONE
@Composable
fun TopScreen(
    currentPokemon: Pokemon,
    updatingProgress: Float,
    downloadComplete: Boolean,
    typeColor: (String) -> Color,
    modifier: Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                end = 2.dp,
                start = 2.dp,
                bottom = 2.dp
            ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        if (downloadComplete) {         // #####    downloadComplete

            // SHOW POKEMON INFO
            val bigSpriteUrl = currentPokemon.sprites[0]
            val pokemonName = currentPokemon.name
            val pokemonDescription = currentPokemon.description

            // TOP SCREEN - TOP ROW
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(PokemonWhite)
            ) {
                // POKEMON IMAGE +++ DONE
                PokemonImageTopInfoScreen(modifier = Modifier.weight(.8f), bigSpriteUrl, pokemonName)

                // POKEMON INFO COLUMN !!! NEEDS A FOOTPRINT
                PokemonStatColumnTopInfoScreen(modifier=Modifier.weight(1f), currentPokemon, typeColor)

            }

            // TOP SCREEN - BOTTOM ROW +++ DONE
            TopScreenBottomRowDescription(modifier = Modifier.weight(.4f), pokemonDescription)


        } else {
            // SHOW LOADING DATA
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PokemonWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LinearProgressIndicator(
                    progress = updatingProgress,
                    color = PokeTypeWATER,
                    trackColor = PokeTypeDARK,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}



// POKEMON IMAGE +++ DONE
@Composable
fun PokemonImageTopInfoScreen(
    modifier: Modifier,
    spriteUrl: String,
    pokemonName: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ){

        AsyncImage(                                       // REIMPLEMENT THIS
            model = spriteUrl,
            contentDescription = pokemonName,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )

//        Image(                                              // ONLY USED FOR PREVIEW
//            painter = painterResource(id = R.drawable.charizard_big),
//            contentDescription = pokemonName,
//            contentScale = ContentScale.FillBounds,
//            modifier=Modifier.fillMaxSize()
//        )
    }
}

// POKEMON STAT COLUMN !!!  FOOTER NEEDS A FOOTPRINT
@Composable
fun PokemonStatColumnTopInfoScreen(
    modifier: Modifier,
    currentPokemon: Pokemon,
    typeColor: (String) -> Color
) {
    val pokemonId: String = currentPokemon.id
    val pokemonName: String = currentPokemon.name

    val pokemonTypes: List<String> = currentPokemon.types
    val pokemonHeight: String = currentPokemon.height
    val pokemonWeight: String = currentPokemon.weight

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(PokemonWhite),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            StatHeader(modifier = Modifier.weight(.6f), pokemonId, pokemonName)

            StatFooter(typeColor, modifier=Modifier.weight(1f), pokemonTypes, pokemonHeight, pokemonWeight)
        }
    }
}



// STAT HEADER +++ DONE
@Composable
fun StatHeader(
    modifier: Modifier,
    pokemonId: String,
    pokemonName: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ){
        Card(
            modifier = Modifier
                .padding(top = 10.dp, end = 2.dp)
                .fillMaxWidth()
                .height(100.dp)
                .background(PokemonWhite),
            border = BorderStroke(2.dp, Color.Black),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            StatHeaderTopRow(modifier=Modifier.weight(1f), pokemonId, pokemonName)

            Divider(thickness = 2.dp, color = PokemonPink)

            StatHeaderBottomRow(modifier = Modifier.weight(1f))
        }
    }
}

// STAT HEADER - TOP ROW +++ DONE
@Composable
fun StatHeaderTopRow(
    modifier: Modifier,
    pokemonId: String,
    pokemonName: String
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonRed)
    ) {
        Box(
            modifier = Modifier
                .weight(.35f)
                .fillMaxSize()
                .background(PokemonRed),
            contentAlignment = Alignment.Center
        ){
            // POKE BALL
            Image(
                painter = painterResource(id = R.drawable.pokeballsprite),
                contentDescription = stringResource(R.string.pokeball_sprite),
                modifier = Modifier.size(22.dp),
                alignment = Alignment.Center
            )
        }

        Box(modifier = Modifier
            .weight(.5f)
            .fillMaxSize()
            .background(PokemonRed),
            contentAlignment = Alignment.CenterStart
        ) {
            // POKE ID
            Text(
                text = pokemonId,
                color = PokemonWhite,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(PokemonRed),
            contentAlignment = Alignment.CenterStart
        ) {
            // POKE NAME
            Text(
                text = pokemonName,
                color = PokemonWhite,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 10.sp
            )
        }
    }
}

// STAT HEADER - BOTTOM ROW +++ DONE
@Composable
fun StatHeaderBottomRow(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite),
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            text = stringResource(R.string.pokemon_title_string),
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier =Modifier.padding(end = 12.dp)
        )
    }
}





// STAT FOOTER SECTION +++ DONE
@Composable
fun StatFooter(
    typeColor: (String) -> Color,
    modifier: Modifier,
    pokemonTypes: List<String>,
    pokemonHeight: String,
    pokemonWeight: String
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ) {

        StatFooterFootPrintColumn(modifier=Modifier.weight(.5f))

        StatFooterTypeColumn(typeColor, modifier=Modifier.weight(1f), pokemonTypes, pokemonHeight, pokemonWeight)
    }
}

// STAT FOOTER - LEFT COLUMN !!!    NEEDS A FOOTPRINT
@Composable
fun StatFooterFootPrintColumn(modifier: Modifier) {
    Column(
        modifier= modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ) {
        Card(
            modifier= Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .height(75.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(
                modifier= Modifier
                    .fillMaxWidth()
                    .weight(.2f)
                    .background(PokemonRed)
            ){}

            Divider(thickness = 2.dp, color = PokemonPink)

            Box(
                modifier= Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(PokemonWhite),
                contentAlignment = Alignment.Center
            ){
//                Image(
//                    painter = painterResource(id = R.drawable.ho_oh_footprint),
//                    contentDescription = "footprint",
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier.size(40.dp)
//                )
            }
        }
    }
}

// STAT FOOTER - RIGHT COLUMN +++ DONE
@Composable
fun StatFooterTypeColumn(
    typeColor: (String) -> Color,
    modifier: Modifier,
    pokemonTypes: List<String>,
    pokemonHeight: String,
    pokemonWeight: String
) {
    Column(
        modifier= modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ) {
        StatFooterTypeCards(typeColor, modifier=Modifier.weight(.32f), pokemonTypes)

        StatFooterHeightWeight(modifier=Modifier.weight(1f), pokemonHeight, pokemonWeight)
    }
}



// STAT FOOTER - TYPE CARDS +++ DONE
@Composable
fun StatFooterTypeCards(
    typeColor: (String) -> Color,
    modifier: Modifier,
    pokemonTypes: List<String>
) {
    when (pokemonTypes.size) {
        1 -> DisplayOneType(typeColor, modifier, pokemonTypes[0])
        2 -> DisplayTwoTypes(typeColor, modifier, pokemonTypes[0], pokemonTypes[1])
    }
}

@Composable
fun DisplayOneType(
    typeColor: (String) -> Color,
    modifier: Modifier,
    type: String
) {
    val type1 = type.uppercase();           val type1Color = typeColor(type1)

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite),
        verticalAlignment = Alignment.Top
    ) {
        Card(
            modifier= Modifier
                .padding(
                    top = 5.dp,
                    start = 35.dp,
                    end = 30.dp
                )
                .height(40.dp)
                .weight(1f)
                .fillMaxWidth()
                .background(PokemonWhite),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(type1Color),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(
                modifier=Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    color = PokemonWhite,
                    fontSize = 8.sp
                )
            }

        }
    }
}

@Composable
fun DisplayTwoTypes(
    typeColor: (String) -> Color,
    modifier: Modifier,
    typeOne: String,
    typeTwo: String
) {
    val type1 = typeOne.uppercase();            val type2 = typeTwo.uppercase()
    val type1Color = typeColor(type1);          val type2Color = typeColor(type2)

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(PokemonWhite)
    ) {
        Card(
            modifier= Modifier
                .padding(
                    top = 5.dp,
                    start = 2.dp,
                    end = 1.dp
                )
                .weight(1f)
                .fillMaxWidth()
                .height(40.dp)
                .background(PokemonWhite),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(type1Color),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(
                modifier=Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    color = PokemonWhite,
                    fontSize = 8.sp
                )
            }

        }

        Card(
            modifier= Modifier
                .padding(
                    top = 5.dp,
                    start = 1.dp,
                    end = 2.dp
                )
                .weight(1f)
                .fillMaxWidth()
                .height(40.dp)
                .background(PokemonWhite),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = CardDefaults.cardColors(type2Color),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(
                modifier=Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type2,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    color = PokemonWhite,
                    fontSize = 8.sp
                )
            }
        }
    }
}


// STAT FOOTER - HEIGHT/WEIGHT CARD +++ DONE
@Composable
fun StatFooterHeightWeight(
    modifier: Modifier,
    pokemonHeight: String,
    pokemonWeight: String
) {
    Box(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .background(PokemonWhite),
        contentAlignment = Alignment.TopCenter
    ){
        Card(
            modifier= Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(end = 2.dp),
            shape= RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(PokemonWhite)
        ) {

            Row(
                modifier= Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = stringResource(R.string.pokemon_height),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = pokemonHeight,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }

            Divider(color = PokemonLightBlue)

            Row(
                modifier= Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = stringResource(R.string.pokemon_weight),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = pokemonWeight,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }
        }
    }
}



// TOP SCREEN - BOTTOM ROW +++ DONE
@Composable
fun TopScreenBottomRowDescription(modifier: Modifier, pokemonDescription: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PokemonWhite)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp, start = 2.dp, end = 2.dp)
                    .background(PokemonWhite),
                border = BorderStroke(3.dp, Color.Black),
                colors = CardDefaults.cardColors(Color.Yellow),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PokemonRed)
                ) {
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        color = PokemonRed
                    )

                    Card(
                        modifier = Modifier
                            .weight(20f)
                            .fillMaxSize()
                            .padding(4.dp),
                        border = BorderStroke(2.dp, PokemonPink),
                        shape = RoundedCornerShape(3.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PokemonWhite)
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = pokemonDescription,
                                modifier = Modifier
                                    .background(PokemonWhite),
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        color = PokemonRed
                    )
                }
            }
        }
    }
}