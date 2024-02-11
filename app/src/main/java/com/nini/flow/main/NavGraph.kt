package com.nini.flow.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nini.flow.characters.CharacterDetailRoute
import com.nini.flow.characters.CharactersRoute
import com.nini.flow.episodes.EpisodesRoute
import com.nini.flow.locations.LocationsRoute
import com.nini.flow.main.Destinations.CHARACTERS_ROUTE
import com.nini.flow.main.Destinations.CHARACTER_ROUTE
import com.nini.flow.main.Destinations.EPISODES_ROUTE
import com.nini.flow.main.Destinations.LOCATIONS_ROUTE
import com.nini.flow.main.Destinations.WELCOME_ROUTE
import com.nini.flow.welcome.TYPE
import com.nini.flow.welcome.WelcomeRoute

object Destinations {
    const val CHARACTERS_ROUTE = "characters"
    const val CHARACTER_ROUTE = "character/{characterId}"
    const val EPISODES_ROUTE = "episodes"
    const val WELCOME_ROUTE = "welcome"
    const val LOCATIONS_ROUTE = "locations"
}
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = WELCOME_ROUTE) {
        composable(WELCOME_ROUTE) {
            WelcomeRoute(onClick = {
                when(it){
                    TYPE.CHARACTER -> navController.navigate(CHARACTERS_ROUTE)
                    TYPE.EPISODES -> navController.navigate(EPISODES_ROUTE)
                    TYPE.LOCATION -> navController.navigate(LOCATIONS_ROUTE)
                }
            })
        }
        composable(CHARACTERS_ROUTE) {
            CharactersRoute(onClick = {
                navController.navigate("character/$it")
            }, onBack = {
                    navController.navigate(WELCOME_ROUTE)
                })
        }
        composable(CHARACTER_ROUTE){
            val id  = it.arguments?.getString("characterId")
            CharacterDetailRoute(id!!.toInt(), onBack = {
                navController.navigate(CHARACTERS_ROUTE)
            })
        }
        composable(LOCATIONS_ROUTE) {
            LocationsRoute(onBack = {
                navController.navigate(WELCOME_ROUTE)
            })
        }
        composable(EPISODES_ROUTE) {
            EpisodesRoute(onBack = {
                navController.navigate(WELCOME_ROUTE)
            })
        }
    }
}