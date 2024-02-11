package com.nini.flow.welcome

import androidx.compose.runtime.Composable

@Composable
fun WelcomeRoute(onClick:(type:TYPE) -> Unit) {
    WelcomeScreen(onClick)
}

enum class TYPE{
    CHARACTER, LOCATION, EPISODES
}