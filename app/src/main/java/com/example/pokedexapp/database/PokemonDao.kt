package com.example.pokedexapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedexapp.models.Pokemon


/**
 * Database Access Object (DAO)
 * used for describing database
 * interaction methods - Implemented
 * by database Repository.
 */
@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertPokemon(pokemon: Pokemon)

    @Query("""
        SELECT COUNT(*) FROM pokemon_table
    """) fun countAllPokemon(): Int

    @Query("""
        SELECT * FROM POKEMON_TABLE
    """) fun getPokemonList(): List<Pokemon>

    @Query("""
        SELECT * FROM pokemon_table
        WHERE name LIKE :name
    """) fun findPokemon(name: String): List<Pokemon>

    @Query("""
        SELECT * FROM POKEMON_TABLE
    """) fun getPokemonLiveDataList(): LiveData<List<Pokemon>>
}