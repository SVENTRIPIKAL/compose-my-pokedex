package com.example.pokedexapp

import com.example.pokedexapp.network.FakeApiBase
import com.example.pokedexapp.network.FakeApiDesc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.DecimalFormat


@Serializable
data class Pokemon(
    val abilities: List<String>,//$
    val forms: List<String>,//$
    val height: String,//$
    val id: String,//$
    val name: String,//$
    val sprites: List<String>,//$
    val stats: Map<String, Int>,//$
    val types: List<String>,//$
    val weight: String,//$
    val desc: String//$
){
    fun matchingSearch(query: String): Boolean {
        val updatedQuery = query.replace("[\\p{Blank}\\p{Punct}]+".toRegex(), "")
        return name.contains(updatedQuery, ignoreCase = true)
    }
}
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
data class PokemonAbilities(
    val abilities: List<String>
)
@Serializable
data class PokemonForms(
    val forms: List<String>
)
@Serializable
data class PokemonSprites(
    val sprites: List<String>
)
@Serializable
data class PokemonStats(
    val stats: Map<String, Int>
)
@Serializable
data class PokemonTypes(
    val types: List<String>
)


//val baseInfoApi = "https://pokeapi.co/api/v2/pokemon/<id>"
//val descriptionApi = "https://pokeapi.co/api/v2/pokemon-species/<id>"
//  [flavor_text_entries] List<JsonObj> | key=14 | [flavor_text] | <String>



// STEP#1
fun getPokemonBase(
    fakeApi: String
): PokemonBase {
    return json.decodeFromString(fakeApi) // https://pokeapi.co/api/v2/pokemon/<id>
}
// POKEMON DESCRIPTION
fun getPokemonDescription(
    fakeApi: String
): String {
    val jsonObj = json.decodeFromString<JsonObject>(fakeApi)
    val entries = jsonObj["flavor_text_entries"]?.jsonArray?.get(14)?.jsonObject?.get("flavor_text")!!
    val descriptor = json.decodeFromJsonElement<String>(entries)
    return descriptor
        .replace("\n", " ", true)
        .replace("\\f"," ", true)
}
// STEP#2
fun getAbilities(
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
// STEP#3-A
fun getForms(
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
// STEP#3-B
fun convertHeight(
    pokemonBase: PokemonBase
): String {

    // METERS TO FEET
    // [ 20 = 2.0 * 3.035 = 6’07” ]
    val supplement = 3.035
    val meters = pokemonBase.height.toString()

    val suffix = meters.last()
    val decimal = meters.dropLast(1).plus(".${suffix}").toDouble()

    val formatter = DecimalFormat("#,###.00")
    val total = formatter.format(decimal * supplement ).toString()

    return total.replace(".", "'").plus("\"")
}
// STEP#4
fun getSprites(
    pokemonBase: PokemonBase
): List<String> {

    val jsonElement = pokemonBase.sprites.jsonObject["front_default"]!!

    val bigSprite = jsonElement.let { json.decodeFromJsonElement<String>(it) }

    val smallSprite = "https://img.pokemondb.net/sprites/sword-shield/icon/${pokemonBase.name}.png"

    return listOf(bigSprite, smallSprite)
}
// STEP#5
fun getStats(
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
// STEP#6-A
fun getTypes(
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
// STEP#6-B
fun convertWeight(
    pokemonBase: PokemonBase
): String {

    // KILOGRAMS TO POUNDS
    // [ 1220 = 122.0 * 2.205 = 269.0 ]
    val supplement = 2.205
    val kilograms = pokemonBase.weight.toString()

    val suffix = kilograms.last()
    val decimal = kilograms.dropLast(1).plus(".${suffix}").toDouble()

    val formatter = DecimalFormat("#,###.0")
    return formatter.format( (decimal * supplement) ).toString().plus(" lbs")
}
// LAST
fun getPokemon(
    fakeApi: String,
    fakeApiDesc: String
): Pokemon {

    val basePokemon = json.decodeFromString<PokemonBase>(fakeApi)

    val abilities = getAbilities(basePokemon)
    val forms = getForms(basePokemon)
    val height = convertHeight(basePokemon)
    val id = basePokemon.id.toString().padStart(4, '0')
    val name = basePokemon.name.uppercase()
    val sprites = getSprites(basePokemon)
    val stats = getStats(basePokemon)
    val types = getTypes(basePokemon)
    val weight = convertWeight(basePokemon)
    val desc = getPokemonDescription(fakeApiDesc)

    return Pokemon(abilities, forms, height, id, name, sprites, stats, types, weight, desc)
}










val json: Json = Json {ignoreUnknownKeys=true}

val parsedPokemonBase = json.decodeFromString<PokemonBase>(FakeApiBase)


// WHEN_THIS-HAPPENS_SHOULD_EXPECTED-OUTCOME
class PokemonBaseApiUnitTests {

    @Test fun when_parsingApi_should_serializePokemon() {

        val actual = getPokemonBase(FakeApiBase)

        assertTrue(actual.id == 150)
        assertTrue(actual.name == "mewtwo")
        assertTrue(actual.height == 20)
        assertTrue(actual.weight == 1220)
    }

    @Test fun when_extractingAbilities_should_returnPokemonAbilitiesSet(){

        val test = listOf("pressure", "unnerve")
        val expected = PokemonAbilities(test)

        val abilities = getAbilities(parsedPokemonBase)
        val actual = PokemonAbilities(abilities)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }


    @Test fun when_extractingForms_should_returnPokemonFormsSet() {

        val test = listOf("mewtwo")
        val expected = PokemonForms(test)

        val forms = getForms(parsedPokemonBase)
        val actual = PokemonForms(forms)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }


    @Test fun when_extractingSprites_should_returnPokemonSpritesSet() {

        val test = listOf(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png",
            "https://img.pokemondb.net/sprites/sword-shield/icon/mewtwo.png"
        )
        val expected = PokemonSprites(test)

        val sprites = getSprites(parsedPokemonBase)
        val actual = PokemonSprites(sprites)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }


    @Test fun when_extractingStats_should_returnPokemonStatMap() {

        val test = mapOf(
            "hp" to 106,
            "attack" to 110,
            "defense" to 90,
            "special-attack" to 154,
            "special-defense" to 90,
            "speed" to 130
        )
        val expected = PokemonStats(test)

        val stats = getStats(parsedPokemonBase)
        val actual = PokemonStats(stats)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }


    @Test fun when_extractingTypes_should_returnPokemonTypesSet() {

        val test = listOf("psychic")
        val expected = PokemonTypes(test)

        val types = getTypes(parsedPokemonBase)
        val actual = PokemonTypes(types)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }

    @Test fun when_parsingApi_should_returnCompletedPokemonDataClass() {

        val test = getPokemon(FakeApiBase, FakeApiDesc)

        println(
            """
                ABILITIES:  ${test.abilities}
                FORMS:      ${test.forms}
                HEIGHT:     ${test.height}
                ID:         ${test.id}
                NAME:       ${test.name}
                SPRITES:    ${test.sprites}
                STATS:      ${test.stats}
                TYPES:      ${test.types}
                WEIGHT:     ${test.weight}
                DESCRIPTOR: ${test.desc}
            """.trimIndent()
        )
    }

    @Test fun when_convertingHeightInMeters_should_returnStringInFeet() {

        val expected = "6'07\""
        val actual = convertHeight(parsedPokemonBase)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }

    @Test fun when_convertingWeightInKilos_should_returnStringInPounds() {

        val expected = "269.0 lbs"
        val actual = convertWeight(parsedPokemonBase)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }

    @Test fun when_gettingPokemonDescription_should_returnValidString() {
        val expected = "Because its battle abilities were raised to the ultimate level, it thinks only of defeating its foes."

        val actual = getPokemonDescription(FakeApiDesc)

        println("Expected:  $expected")
        println("Actual:    $actual")

        assertEquals(expected, actual)
    }
}

class SearchPokemonByNameTests {

    private val searchText = MutableStateFlow("")
    private val isSearching = MutableStateFlow(false)
    private val searchList = MutableStateFlow(emptyList<Pokemon>())


    @OptIn(FlowPreview::class)
    suspend fun checkFunction(): List<Pokemon> {
        val search = searchText
            .debounce(1000L)
            .onEach { isSearching.update { true } }
            .combine(searchList) { query, pokemonList ->
                if (query.isBlank()) emptyList()
                else pokemonList.filter { it.matchingSearch(query) }
            }.firstOrNull()

        return search ?: emptyList()
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test fun whenSearchName_ShouldReturnListWithPokemon() = runTest {

        val pokemon = getPokemon(FakeApiBase, FakeApiDesc)
        val expected = listOf(pokemon)

        searchText.value = " m!e%w!t_w+o "
        searchList.value = expected

        val actual = checkFunction()

        assertEquals(expected, actual)
    }
}
