package com.nini.flow.episodes

import com.nini.flow.core.network.RemoteDataSource

class EpisodesRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getEpisodes(page:Int): EpisodesResponse {
        return remoteDataSource.getEpisodes(page)
    }

}