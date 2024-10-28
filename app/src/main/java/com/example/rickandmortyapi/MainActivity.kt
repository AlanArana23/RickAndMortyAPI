package com.example.rickandmortyapi // Asegúrate de que este paquete coincida con el del manifiesto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmortyapi.ui.screens.CharacterDetailScreen
import com.example.rickandmortyapi.ui.screens.HomeScreen
import com.example.rickandmortyapi.ui.theme.RickAndMortyAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            RickAndMortyAPITheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(navController = navController, startDestination = "home"){
                        composable(route = "home"){
                            HomeScreen(innerPadding, navController) // recibe y hace la navegacion
                        }
                        composable(
                            route = "detail/{id}",
                            arguments = listOf(
                                navArgument("id"){
                                    type = NavType.IntType
                                    nullable = false
                                }
                            )
                        ){
                            val id = it.arguments?.getInt("id") ?: 0
                            CharacterDetailScreen(id = id, innerPaddingValues = innerPadding)
                        }
                    }
                }
            }
        }
    }
}