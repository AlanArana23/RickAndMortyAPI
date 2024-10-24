package com.example.rickandmortyapi.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(){
//    GlobalScope.launch {
//        println("Hola desde el launch")
//        delay(2000)
//        println("Termino de traerse los datos")
//    }
//   cLaunch()
    cAsync()

}

fun cLaunch(){
    runBlocking {
        launch {
//            println("Trayendo informacion de una API")
//            delay(2000)
//            println("Finalizo la consulta de la API")
            println("Mi super aplocacion")
            val data = consultaBaseDeDatos()
            println(data)
        }
    }
}

fun cAsync(){
    runBlocking {
        val result = async {
            println("Consultando Datos")
            delay(3000)
            20
        }
        println(result.await())
    }
}

suspend fun consultaBaseDeDatos() : String{
    println("Consultando base de Datos")
    delay(2000)
    return "Datos Traidos"
}