package com.example.rickandmortyapi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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
        // Fondo inspirado en Rick and Morty (verde neón)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF00FF99)), // Color verde neón de fondo
            color = Color(0xFF7EC5A9) // Fondo de toda la pantalla
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPaddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Centrar contenido horizontalmente
            ) {
                // Imagen del personaje con esquinas redondeadas
                Image(
                    painter = rememberAsyncImagePainter(character.image),
                    contentDescription = "Character Image",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(100.dp)) // Esquinas redondeadas
                        .background(Color.White) // Fondo blanco detrás de la imagen
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre del personaje
                Text(
                    text = character.name,
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color(0xFF002366) // Azul oscuro para el nombre
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Detalles organizados con espaciado adecuado y estilo limpio
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start // Alinear detalles a la izquierda
                ) {
                    DetailText(label = "Status", value = character.status)
                    DetailText(label = "Species", value = character.species)
                    DetailText(label = "Gender", value = character.gender)
                    DetailText(label = "Location", value = character.location?.name ?: "Unknown")
                    DetailText(label = "Origin", value = character.origin?.name ?: "Unknown")
                    DetailText(label = "Created", value = character.created)
                }
            }
        }
    }
}

// Composable auxiliar para un estilo limpio en los detalles
@Composable
fun DetailText(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label:",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.DarkGray
            )
        )
        Text(
            text = value,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                color = androidx.compose.ui.graphics.Color.Gray
            )
        )
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