package com.nini.flow.core.network

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getCharacters(page:Int) = apiService.getCharacters(page)
    suspend fun getLocations(page:Int) = apiService.getLocations(page)
    suspend fun getEpisodes(page:Int) = apiService.getEpisodes(page)
    suspend fun getCharacter(id:Int) = apiService.getCharacter(id)
}