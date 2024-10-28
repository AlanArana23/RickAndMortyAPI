package com.example.rickandmortyapi.services

import com.example.rickandmortyapi.models.ApiResponse
import com.example.rickandmortyapi.models.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character") // Cambio a "character" (sin "s" al final)
    suspend fun getCharacters(): ApiResponse

    @GET("character/{id}") // También cambio aquí a "character"
    suspend fun getCharacterById(@Path("id") id: Int): Character
}