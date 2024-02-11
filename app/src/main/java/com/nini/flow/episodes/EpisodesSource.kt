package com.nini.flow.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

class EpisodesSource(private val episodesRepository: EpisodesRepository): PagingSource<Int,EpisodesResponse.Episode>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesResponse.Episode> {
        return try {
            val page = params.key ?: 1
            val itemList = episodesRepository.getEpisodes(page)
            val nextKey = itemList.info?.pages?.let { if (page >= itemList.info.pages) null else page.plus(1) }
            LoadResult.Page(
                data = itemList.results ?: emptyList(),
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

    override fun getRefreshKey(state: PagingState<Int,EpisodesResponse.Episode>): Int? {
        return state.anchorPosition
    }
}