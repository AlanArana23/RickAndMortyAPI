package com.example.rickandmortyapi.models



data class ApiResponse(
    val info: Info,
    val results: List<Character>
)