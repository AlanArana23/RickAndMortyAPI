package com.example.rickandmortyapi.services

import com.example.rickandmortyapi.models.ApiResponse
import com.example.rickandmortyapi.models.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character") //no es lischaracter es ApiResponse para tener los datos de la api
    suspend fun getCharacters(): ApiResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}