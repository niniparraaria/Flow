package com.nini.flow.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

class CharacterSource(private val charactersRepository: CharactersRepository): PagingSource<Int,CharactersResponse.Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponse.Character> {
        return try {
            val page = params.key ?: 1
            val characterList =charactersRepository.getCharacters(page)
            val nextKey = characterList.info?.pages?.let { if (page >= characterList.info.pages) null else page.plus(1) }
            LoadResult.Page(
                data = characterList.results ?: emptyList(),
                prevKey = if (page <= 1) null else page.minus(1),
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

    override fun getRefreshKey(state: PagingState<Int, CharactersResponse.Character>): Int? {
        return state.anchorPosition
    }
}