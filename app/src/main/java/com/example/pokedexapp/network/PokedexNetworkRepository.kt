package com.example.pokedexapp.network

import com.example.pokedexapp.models.Pokemon
import com.example.pokedexapp.models.PokemonBase
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.DecimalFormat


interface PokedexNetworkRepository {
    suspend fun sendBaseRequest(id: Int): String
    suspend fun sendDescRequest(id: Int): String
}

// val baseInfoApi = "https://pokeapi.co/api/v2/pokemon/<id>"
// val descriptionApi = "https://pokeapi.co/api/v2/pokemon-species/<id>"


private const val baseUrl = "https://pokeapi.co/api/v2/"

private val json = Json { ignoreUnknownKeys=true }


class PokedexRepositoryApi: PokedexNetworkRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val retrofitService by lazy { retrofit.create(PokedexNetworkApi::class.java) }

    override suspend fun sendBaseRequest(id: Int): String {
        return retrofitService.sendBaseRequest(id)
    }

    override suspend fun sendDescRequest(id: Int): String {
        return retrofitService.sendDescRequest(id)
    }
}


private fun getAbilities(
    pokemonBase: PokemonBase
): List<String> {

    val abilities = mutableListOf<String>()

    val jsonObjectList = json.decodeFromJsonElement<List<JsonObject>>(pokemonBase.abilities)

    jsonObjectList.forEach {

        val x = it["ability"]?.jsonObject?.get("name")

        val y = x?.let { jsonElement -> json.decodeFromJsonElement<String>(jsonElement) }

        abilities.add(y!!)
    }

    return abilities.toList()
}

private fun getForms(
    pokemonBase: PokemonBase
): List<String> {

    val forms = mutableListOf<String>()

    val jsonObjectList = json.decodeFromJsonElement<List<JsonObject>>(pokemonBase.forms)

    jsonObjectList.forEach {

        val formName = it["name"]?.let { jsonElement -> json.decodeFromJsonElement<String>(jsonElement) }

        forms.add(formName!!)
    }

    return forms.toList()
}

private fun convertHeight(
    pokemonBase: PokemonBase
): String {

    // METERS TO FEET
    // [ 20 = 2.0 * 3.035 = 6’07” ]
    val supplement = 3.035
    val meters = pokemonBase.height.toString()

    val suffix = meters.last()
    val decimal = meters.dropLast(1).plus(".${suffix}").toDouble()

    val total = decimal * supplement

    val formatter = DecimalFormat("#,###.00")
    val totalString = formatter.format( total ).toString()

    val oldValue = "."
    val other = "\""

    val newValue = if (total < 1) "" else "'"

    return totalString.replace(oldValue, newValue).plus(other)
}

private fun getSprites(
    pokemonBase: PokemonBase
): List<String> {

    val jsonElement = pokemonBase.sprites.jsonObject["front_default"]!!

    val bigSprite = jsonElement.let { json.decodeFromJsonElement<String>(it) }

    val smallSprite = "https://img.pokemondb.net/sprites/sword-shield/icon/${pokemonBase.name}.png"

    return listOf(bigSprite, smallSprite)
}

private fun getStats(
    pokemonBase: PokemonBase
): Map<String, Int> {

    val stats = mutableMapOf<String, Int>()

    val jsonObjectList = json.decodeFromJsonElement<List<JsonObject>>(pokemonBase.stats)

    jsonObjectList.forEach {

        val number = it["base_stat"]!!

        val name = it["stat"]?.jsonObject?.get("name")!!

        val statNumber = json.decodeFromJsonElement<Int>(number)
        val statName = json.decodeFromJsonElement<String>(name)

        stats[statName] = statNumber
    }

    return stats.toMap()
}

private fun getTypes(
    pokemonBase: PokemonBase
): List<String> {

    val types = mutableSetOf<String>()

    val jsonObjectList = json.decodeFromJsonElement<List<JsonObject>>(pokemonBase.types)

    jsonObjectList.forEach {
        val jsonElement = it["type"]?.jsonObject?.get("name")!!

        val typeString = json.decodeFromJsonElement<String>(jsonElement)

        types.add(typeString)
    }

    return types.toList()
}

private fun convertWeight(
    pokemonBase: PokemonBase
): String {

    // KILOGRAMS TO POUNDS
    // [ 1220 = 122.0 * 2.205 = 269.0 ]
    val supplement = 2.205
    val kilograms = pokemonBase.weight.toString()

    val suffix = kilograms.last()
    val decimal = kilograms.dropLast(1).plus(".${suffix}").toDouble()

    val total = decimal * supplement

    val formatter = DecimalFormat("#,##0.0")
    return formatter.format( total ).toString().plus(" lbs")
}

private fun getPokemonDescription(
    fakeApi: String
): String {
    val jsonObj = json.decodeFromString<JsonObject>(fakeApi)
    val entries = jsonObj["flavor_text_entries"]?.jsonArray?.get(14)?.jsonObject?.get("flavor_text")!!
    val descriptor = json.decodeFromJsonElement<String>(entries)
    return descriptor
        .replace("\n", " ", true)
        .replace("\\f"," ", true)
}



fun getPokemon(
    ApiBase: String,
    ApiDesc: String
): Pokemon {

    val pokemonBaseJsonElm = json.decodeFromString<PokemonBase>(ApiBase)

    val abilities = getAbilities(pokemonBaseJsonElm)
    val forms = getForms(pokemonBaseJsonElm)
    val height = convertHeight(pokemonBaseJsonElm)
    val id = pokemonBaseJsonElm.id.toString().padStart(4, '0')
    val name = pokemonBaseJsonElm.name.uppercase()
    val sprites = getSprites(pokemonBaseJsonElm)
    val stats = getStats(pokemonBaseJsonElm)
    val types = getTypes(pokemonBaseJsonElm)
    val weight = convertWeight(pokemonBaseJsonElm)
    val desc = getPokemonDescription(ApiDesc)

    return Pokemon(
        abilities = abilities,
        forms = forms,
        height = height,
        id = id,
        name = name,
        sprites = sprites,
        stats = stats,
        types = types,
        weight = weight,
        description = desc
    )
}