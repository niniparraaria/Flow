package com.nini.flow.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

class LocationsSource(private val locationsRepository: LocationsRepository): PagingSource<Int,LocationsResponse.Location>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationsResponse.Location> {
        return try {
            val page = params.key ?: 1
            val itemList = locationsRepository.getLocations(page)
            val nextKey = itemList.info?.pages?.let { if (page >= itemList.info.pages) null else page.plus(1) }
            LoadResult.Page(
                data = itemList.results?: emptyList(),
                prevKey =  if (page <= 1) null else page.minus(1),
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: RuntimeException) {
            return LoadResult.Error(exception)
        } catch (exception: NullPointerException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int,LocationsResponse.Location>): Int? {
        return state.anchorPosition
    }
}