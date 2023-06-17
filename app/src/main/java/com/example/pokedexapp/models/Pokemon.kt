package com.example.pokedexapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement



@Serializable
data class PokemonBase(
    val abilities: JsonElement,
    val forms: JsonElement,
    val height: Int,
    val id: Int,
    val name: String,
    val sprites: JsonElement,
    val stats: JsonElement,
    val types: JsonElement,
    val weight: Int
)

@Serializable
@Entity("pokemon_table")
data class Pokemon(
    @PrimaryKey @ColumnInfo("ID") val id: String = "",
    @ColumnInfo("Name") val name: String = "",
    @ColumnInfo("Forms") val forms: List<String> = emptyList(),
    @ColumnInfo("Sprites") val sprites: List<String> = emptyList(),
    @ColumnInfo("Types") val types: List<String> = emptyList(),
    @ColumnInfo("Stats") val stats: Map<String, Int> = emptyMap(),
    @ColumnInfo("Height") val height: String = "",
    @ColumnInfo("Weight") val weight: String = "",
    @ColumnInfo("Abilities") val abilities: List<String> = emptyList(),
    @ColumnInfo("Description") val description: String = ""
){
    fun matchFound(text: String): Boolean {
        val updatedSearch = text.replace("[\\p{Blank}\\p{Punct}]+".toRegex(), "")
        return this.name.contains(updatedSearch, ignoreCase = true)
    }
}