package com.example.pokedexapp.network

import retrofit2.http.GET
import retrofit2.http.Path



interface PokedexNetworkApi {

    @GET("pokemon/{id}") suspend fun sendBaseRequest(@Path("id") id: Int): String

    @GET("pokemon-species/{id}") suspend fun sendDescRequest(@Path("id") id: Int): String
}