package com.example.pokedexapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedexapp.database.util.Converters
import com.example.pokedexapp.models.Pokemon


/**
 * Database used to store/restore information
 * upon application startup/destruction.
 * Allows for only one instance of the
 * database to be created at a time.
 * Inherits from RoomDatabase.
 */
@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokedexDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {

        private var INSTANCE: PokedexDatabase? = null

        fun getInstance(context: Context): PokedexDatabase {

            var instance = INSTANCE

            if (instance==null) {

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokedexDatabase::class.java,
                    "pokedex_database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
            }

            return instance
        }
    }
}