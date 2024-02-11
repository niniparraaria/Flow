package com.nini.flow.characters

import androidx.compose.runtime.Composable

@Composable
fun CharactersRoute(onClick:(id:Int) -> Unit, onBack:() -> Unit) {
    CharacterScreen(onClick = onClick, onBack = onBack)
}

@Composable
fun CharacterDetailRoute(id:Int, onBack:() -> Unit) {
    CharacterDetailScreen(id = id, onBack = onBack)
}
