package com.nini.flow.core.modules

import com.nini.flow.characters.CharacterDetailViewModel
import com.nini.flow.characters.CharactersViewModel
import com.nini.flow.episodes.EpisodesViewModel
import com.nini.flow.locations.LocationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel{ CharactersViewModel(get()) }
    viewModel{ CharacterDetailViewModel(get()) }
    viewModel { EpisodesViewModel(get()) }
    viewModel { LocationsViewModel(get()) }
}