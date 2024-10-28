package com.example.rickandmortyapi // Asegúrate de que este paquete coincida con el del manifiesto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapi.ui.screens.CharacterDetailScreen
import com.example.rickandmortyapi.ui.screens.HomeScreen
import com.example.rickandmortyapi.ui.theme.RickAndMortyAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAPITheme {
                // Creamos un NavController para manejar la navegación
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Configuramos la navegación
                    NavGraph(navController = navController, innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(innerPadding) // Aplicamos padding
    ) {
        // Pantalla principal de la lista de personajes
        composable("home") {
            HomeScreen(innerPadding = innerPadding, navController = navController)
        }

        // Pantalla de detalles de un personaje
        composable("detail/{id}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            CharacterDetailScreen(id = characterId, innerPaddingValues = innerPadding)
        }
    }
}