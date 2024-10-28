package com.example.rickandmortyapi.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rickandmortyapi.models.Character
import com.example.rickandmortyapi.services.CharacterService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var characters by remember { mutableStateOf(listOf<Character>()) }
    var isLoading by remember { mutableStateOf(false) }

    // Llamada a la API
    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                isLoading = true
                val BASE_URL = "https://rickandmortyapi.com/api/"
                val characterService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CharacterService::class.java)
                characters = characterService.getCharacters()
                Log.i("HomeScreenResponse", characters.toString())
                isLoading = false
            } catch (e: Exception) {
                characters = listOf()
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(characters) { character ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(200.dp)
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("detail/${character.id}")
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = character.name,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AsyncImage(model: String, contentDescription: String, modifier: Modifier, contentScale: ContentScale) {

}
