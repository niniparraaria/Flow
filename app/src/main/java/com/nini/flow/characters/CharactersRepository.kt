package com.nini.flow.characters

import android.content.Context
import com.nini.flow.core.network.NetworkResult
import com.nini.flow.core.network.RemoteDataSource
import com.nini.flow.core.network.toResult
import kotlinx.coroutines.flow.Flow

class CharactersRepository(private val remoteDataSource: RemoteDataSource, private val context: Context) {


    suspend fun getCharacters(page:Int): CharactersResponse {
           return remoteDataSource.getCharacters(page)
    }
    suspend fun getCharacter(id:Int): Flow<NetworkResult<CharactersResponse.Character>> {
        return toResult(context){
            remoteDataSource.getCharacter(id)
        }
    }


}