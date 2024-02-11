package com.nini.flow.core.modules

import com.nini.flow.characters.CharactersRepository
import com.nini.flow.episodes.EpisodesRepository
import com.nini.flow.locations.LocationsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory {  CharactersRepository(get(), androidContext()) }
    factory {  EpisodesRepository(get())}
    factory {  LocationsRepository(get()) }
}