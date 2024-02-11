package com.nini.flow.episodes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.nini.flow.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class EpisodesSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var repository: EpisodesRepository
    lateinit var episodesSource: EpisodesSource
    companion object {
        val characters = listOf( "url1", "url2")
        val episode1 = EpisodesResponse.Episode(airDate = "date", characters = characters, created = "created", episode = "episode 1", name = "name", id = 1, url = "url" )
        val episode2 = EpisodesResponse.Episode(airDate = "date 2", characters = characters, created = "created 2", episode = "episode 2", name = "name 2", id = 2, url = "url 2" )
        val info1: EpisodesResponse.Info = EpisodesResponse.Info(count = 1, pages = 1, prev = null, next = "next")
        val info2: EpisodesResponse.Info = EpisodesResponse.Info(count = 1, pages = 2, prev = "prev", next = null)
        val response = EpisodesResponse(info1, listOf(episode1))
        val nextResponse = EpisodesResponse(info2, listOf(episode2))
    }
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        episodesSource = EpisodesSource(repository)
    }
    @Test
    fun `episodes paging source load - failure - http error`() = runTest {
        val error = RuntimeException("410", Throwable())
        given(repository.getEpisodes(anyInt())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, EpisodesResponse.Episode>(error)
        assertEquals(
            expectedResult, episodesSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `episodes paging source load - failure - null`() = runTest {
        val error = NullPointerException("Cannot invoke \"com.nini.flow.episodes.EpisodesResponse.getInfo()\" because \"itemList\" is null")
        given(repository.getEpisodes(anyInt())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, EpisodesResponse.Episode>(error)
        assertEquals(
            expectedResult.toString(), episodesSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `episodes paging source load refresh - success`() = runTest {
        given(repository.getEpisodes(anyInt())).willReturn(response)
        val expectedResult = PagingSource.LoadResult.Page<Int, EpisodesResponse.Episode>(
            data = response.results ?: emptyList(),
            nextKey = 1,
            prevKey = null
        )
        assertEquals(
            expectedResult, episodesSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `episodes paging source load append - success`() = runTest {
        given(repository.getEpisodes(anyInt())).willReturn(nextResponse)
        val expectedResult = PagingSource.LoadResult.Page<Int, EpisodesResponse.Episode>(
            data = nextResponse.results ?: emptyList(),
            nextKey = 2,
            prevKey = null
        )
        assertEquals(
            expectedResult, episodesSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `episodes paging source load prepend - success`() = runTest {
        given(repository.getEpisodes(anyInt())).willReturn(response)
        val expectedResult = PagingSource.LoadResult.Page<Int, EpisodesResponse.Episode>(
            data = response.results ?: emptyList(),
            nextKey = 1,
            prevKey = null
        )
        assertEquals(
            expectedResult, episodesSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}