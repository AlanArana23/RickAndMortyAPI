package com.example.rickandmortyapi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmortyapi.models.Character
import com.example.rickandmortyapi.models.Location
import com.example.rickandmortyapi.models.Origin
import com.example.rickandmortyapi.services.CharacterService
import com.example.rickandmortyapi.ui.theme.RickAndMortyAPITheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun CharacterDetailScreen(id: Int, innerPaddingValues: PaddingValues) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    var character by remember {
        mutableStateOf(Character(
            id = 0,
            name = "",
            image = "",
            status = "",
            created = "",
            episode = emptyList(),
            gender = "",
            location = Location(name = "", url = ""),
            origin = Origin(name = "", url = ""),
            species = "",
            type = "",
            url = ""
        ))
    }

    // Llamada a la API para obtener los detalles del personaje
    LaunchedEffect(key1 = true) {
        scope.launch {
            val BASE_URL = "https://rickandmortyapi.com/api/"
            val characterService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)

            isLoading = true
            character = characterService.getCharacterById(id)
            isLoading = false
        }
    }

    if (isLoading) {
        // Muestra un indicador de carga mientras se obtienen los datos
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Muestra los detalles del personaje cuando se haya cargado
        Column(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
        ) {
            Text(text = "Name: ${character.name}")
            Text(text = "Status: ${character.status}")
            Text(text = "Species: ${character.species}")
            Text(text = "Gender: ${character.gender}")
            Text(text = "Location: ${character.location?.name ?: "Unknown"}")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CharacterDetailScreenPreview() {
    RickAndMortyAPITheme {
        CharacterDetailScreen(id = 1, innerPaddingValues = PaddingValues(0.dp))
    }
}