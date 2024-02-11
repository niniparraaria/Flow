package com.nini.flow.locations

import com.nini.flow.core.network.RemoteDataSource

class LocationsRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getLocations(page:Int): LocationsResponse {
        return  remoteDataSource.getLocations(page)
    }

}