package com.nini.flow.core.network

import com.nini.flow.characters.CharactersResponse
import com.nini.flow.episodes.EpisodesResponse
import com.nini.flow.locations.LocationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page:Int): CharactersResponse
    @GET("location")
    suspend fun getLocations(@Query("page") page:Int): LocationsResponse
    @GET("episode")
    suspend fun getEpisodes(@Query("page") page:Int): EpisodesResponse
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id:Int): Response<CharactersResponse.Character>
}