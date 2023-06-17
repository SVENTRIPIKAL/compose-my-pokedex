package com.example.pokedexapp.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.database.PokedexDatabase
import com.example.pokedexapp.database.PokedexRepository
import com.example.pokedexapp.models.Pokemon
import com.example.pokedexapp.network.FakeApiCharizard
import com.example.pokedexapp.network.FakeApiCharizardDesc
import com.example.pokedexapp.network.PokedexRepositoryApi
import com.example.pokedexapp.network.getPokemon
import com.example.pokedexapp.ui.theme.PokeTypeBUG
import com.example.pokedexapp.ui.theme.PokeTypeDARK
import com.example.pokedexapp.ui.theme.PokeTypeDRAGON
import com.example.pokedexapp.ui.theme.PokeTypeELECTRIC
import com.example.pokedexapp.ui.theme.PokeTypeFAIRY
import com.example.pokedexapp.ui.theme.PokeTypeFIGHTING
import com.example.pokedexapp.ui.theme.PokeTypeFIRE
import com.example.pokedexapp.ui.theme.PokeTypeFLYING
import com.example.pokedexapp.ui.theme.PokeTypeGHOST
import com.example.pokedexapp.ui.theme.PokeTypeGRASS
import com.example.pokedexapp.ui.theme.PokeTypeGROUND
import com.example.pokedexapp.ui.theme.PokeTypeICE
import com.example.pokedexapp.ui.theme.PokeTypeNORMAL
import com.example.pokedexapp.ui.theme.PokeTypePOISON
import com.example.pokedexapp.ui.theme.PokeTypePSYCHIC
import com.example.pokedexapp.ui.theme.PokeTypeROCK
import com.example.pokedexapp.ui.theme.PokeTypeSTEEL
import com.example.pokedexapp.ui.theme.PokeTypeWATER
import kotlinx.coroutines.launch


const val ONE = 1
const val INDEX_END = 151
const val ZERO_DECIMAL = 0.0
val PERCENTAGE_INCREASE = String.format("%.4f", (ONE.toDouble() / INDEX_END)).toDouble()


sealed interface UiState {
    object Error: UiState
    data class Success(
        val pokemon: Pokemon,
        val model: MainViewModel
    ): UiState
    data class Preview(
        val pokemon: Pokemon
    ): UiState
}

/**
 * Main ViewModel used for storing
 * in-memory data for the User Interface (UI).
 * Inherits from ViewModel.
 */
class MainViewModel(application: Application): ViewModel() {

    private val currentViewModel: MainViewModel = this
    var uiState: UiState by mutableStateOf(UiState.Success(Pokemon(), this))
        private set

    private lateinit var repository: PokedexRepository

    private var currentPokemon: Pokemon by mutableStateOf(Pokemon())
    private var completionStatus: Boolean by mutableStateOf(false)
    private var completionProgress: Double by mutableStateOf(ZERO_DECIMAL)
    private var pokemonList: List<Pokemon> by mutableStateOf(emptyList())



    init {
        buildDatabase(application)

        assignDefaultValues()

        executeRequests()
    }



    private fun buildDatabase(application: Application) {
        val database = PokedexDatabase.getInstance(application)
        val dao = database.pokemonDao()
        repository = PokedexRepository(dao)
    }

    private fun assignDefaultValues() {
        pokemonList = emptyList()
        currentPokemon = Pokemon()
        completionStatus = false
    }

    private suspend fun updateStartUpValues() {
        pokemonList = getDbPokemonList()
        currentPokemon = pokemonList.first()
        completionStatus = true
    }

    private fun executeRequests() {

        viewModelScope.launch {

            uiState = try {

                pokemonList = getDbPokemonList()

                if (pokemonList.size != INDEX_END) {

                    val networkApi = PokedexRepositoryApi()

                    for(index in ONE .. INDEX_END) {

                        val pokemonBase = networkApi.sendBaseRequest(index)

                        val pokemonDesc = networkApi.sendDescRequest(index)

                        val pokemon = getPokemon(
                            ApiBase = pokemonBase,
                            ApiDesc = pokemonDesc
                        )

                        addPokemonToDb(pokemon)

                        increaseProgressBar()
                    }

                } else repeat(pokemonList.size) { increaseProgressBar() }

                updateStartUpValues()

                UiState.Success(currentPokemon, currentViewModel)

            } catch (_:Exception) {

                UiState.Error
            }
        }
    }



    /**
     * Asynchronous function calls to Room Database
     */
    private suspend fun addPokemonToDb(pokemon: Pokemon) {
        repository.addPokemonAsync(pokemon).await()
    }

    private suspend fun getDbPokemonList(): List<Pokemon> {
        return repository.getCurrentListAsync().await()
    }



    /**
     * Main thread function calls
     */
    private fun increaseProgressBar() {
        completionProgress += PERCENTAGE_INCREASE
    }

    fun getPokeList(): List<Pokemon> {
        return pokemonList
    }

    fun isComplete(): Boolean {
        return completionStatus
    }

    fun updateCompletionProgress(): Float {
        return completionProgress.toFloat()
    }

    val updateCurrentPokemon: (Pokemon) -> Unit = { pokemon ->

        currentPokemon = pokemon
        uiState = UiState.Success(currentPokemon, currentViewModel)
    }

    val typeColor: (String) -> Color = { type ->

        when (type) {
            "NORMAL" -> PokeTypeNORMAL
            "FIRE" -> PokeTypeFIRE
            "WATER" -> PokeTypeWATER
            "ELECTRIC" -> PokeTypeELECTRIC
            "GRASS" -> PokeTypeGRASS
            "ICE" -> PokeTypeICE
            "FIGHTING" -> PokeTypeFIGHTING
            "POISON" -> PokeTypePOISON
            "GROUND" -> PokeTypeGROUND
            "FLYING" -> PokeTypeFLYING
            "PSYCHIC" -> PokeTypePSYCHIC
            "BUG" -> PokeTypeBUG
            "ROCK" -> PokeTypeROCK
            "GHOST" -> PokeTypeGHOST
            "DRAGON" -> PokeTypeDRAGON
            "DARK" -> PokeTypeDARK
            "STEEL" -> PokeTypeSTEEL
            else -> PokeTypeFAIRY
        }
    }

    val boxColor: (Pokemon) -> Color = { pokemon ->

        when(currentPokemon) {
            pokemon -> PokeTypeICE
            else -> PokeTypeSTEEL
        }
    }




    val updateSearchList: (String) -> List<Pokemon> = { text ->
        if (text.isBlank()) pokemonList
        else pokemonList.filter { it.matchFound(text) }
    }

    val updateCurrentPokemonWithSearched: (List<Pokemon>) -> Unit = { list ->
        currentPokemon = list.firstOrNull() ?: currentPokemon

        uiState = UiState.Success(currentPokemon, currentViewModel)
    }


    var onQuit by mutableStateOf(false)
        private set
    fun updateQuitStatus() {
        onQuit = !onQuit
    }

    var onSearch by mutableStateOf(false)
        private set
    fun updateSearchStatus() {
        onSearch = !onSearch
    }
}














/**
 * PREVIEW CLASS
 */
class PreviewModel: ViewModel() {

    val pokemon = getPokemon(
        FakeApiCharizard,
        FakeApiCharizardDesc
    )

    var pokemonList: List<Pokemon> = listOf(
        pokemon,pokemon,pokemon,pokemon,pokemon,
        pokemon,pokemon,pokemon,pokemon,pokemon,
        pokemon,pokemon,pokemon,pokemon,pokemon,
        pokemon,pokemon,pokemon,pokemon,pokemon,
        pokemon,pokemon,pokemon,pokemon,pokemon,
        pokemon,pokemon,pokemon,pokemon,pokemon,
    )


    val currentAllPokemonLiveList = pokemonList

    private var currentPokemon: Pokemon by mutableStateOf(pokemon)
    private var completionStatus: Boolean by mutableStateOf(true)
    private var completionProgress: Double by mutableStateOf(ZERO_DECIMAL)


    fun isComplete(): Boolean {
        return completionStatus
    }

    fun updateCompletionProgress(): Float {
        return completionProgress.toFloat()
    }

    val updateCurrentPokemon: (Pokemon) -> Unit = { pokemon ->

        currentPokemon = pokemon
    }

    val typeColor: (String) -> Color = { type ->

        when (type) {
            "NORMAL" -> PokeTypeNORMAL
            "FIRE" -> PokeTypeFIRE
            "WATER" -> PokeTypeWATER
            "ELECTRIC" -> PokeTypeELECTRIC
            "GRASS" -> PokeTypeGRASS
            "ICE" -> PokeTypeICE
            "FIGHTING" -> PokeTypeFIGHTING
            "POISON" -> PokeTypePOISON
            "GROUND" -> PokeTypeGROUND
            "FLYING" -> PokeTypeFLYING
            "PSYCHIC" -> PokeTypePSYCHIC
            "BUG" -> PokeTypeBUG
            "ROCK" -> PokeTypeROCK
            "GHOST" -> PokeTypeGHOST
            "DRAGON" -> PokeTypeDRAGON
            "DARK" -> PokeTypeDARK
            "STEEL" -> PokeTypeSTEEL
            else -> PokeTypeFAIRY
        }
    }

    val boxColor: (Pokemon) -> Color = { pokemon ->

        when(currentPokemon) {
            pokemon -> PokeTypeICE
            else -> PokeTypeSTEEL
        }
    }
}