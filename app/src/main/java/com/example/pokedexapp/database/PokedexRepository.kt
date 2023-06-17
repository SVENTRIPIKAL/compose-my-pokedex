package com.example.pokedexapp.database

import com.example.pokedexapp.models.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


/**
 * Database repository which implements the
 * Data Access Object (DAO) and defines
 * the functions of the interface.
 */
class PokedexRepository(private val pokemonDao: PokemonDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getCurrentListAsync(): Deferred<List<Pokemon>> =
        coroutineScope.async { pokemonDao.getPokemonList() }

    fun addPokemonAsync(pokemon: Pokemon) =
        coroutineScope.async { pokemonDao.insertPokemon(pokemon) }

}