package com.example.pokedexapp.database.util

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * Converter class which tells Room
 * database how to encode/decode
 * List and Map objects
 */
class Converters {

    @TypeConverter
    fun listStringToJsonString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun jsonStringToListString(string: String): List<String> {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun mapStringIntToJsonString(map: Map<String, Int>): String {
        return Json.encodeToString(map)
    }

    @TypeConverter
    fun jsonStringToMapStringInt(string: String): Map<String, Int> {
        return Json.decodeFromString(string)
    }
}