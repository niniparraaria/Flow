package com.nini.flow.core.modules

import com.nini.flow.characters.CharacterSource
import com.nini.flow.core.network.RemoteDataSource
import com.nini.flow.episodes.EpisodesSource
import com.nini.flow.locations.LocationsSource
import org.koin.dsl.module

val remoteDataSourceModule= module {
    factory {  RemoteDataSource(get()) }
    factory { CharacterSource(get()) }
    factory { EpisodesSource(get()) }
    factory { LocationsSource(get()) }
}