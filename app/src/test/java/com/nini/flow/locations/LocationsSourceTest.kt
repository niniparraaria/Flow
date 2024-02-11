package com.nini.flow.locations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.nini.flow.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LocationsSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var repository: LocationsRepository
    lateinit var locationsSource: LocationsSource
    companion object {
        val residents = listOf( "url1", "url2")
        val location1 = LocationsResponse.Location(created = "created", name = "name", id = 1, url = "url" , dimension = "dimension 1", residents = residents, type = "1")
        val location2 = LocationsResponse.Location(created = "created 2", name = "name 2", id = 2, url = "url 2" , dimension = "dimension 2", residents = residents, type = "2")
        val info1: LocationsResponse.Info = LocationsResponse.Info(count = 1, pages = 1, prev = null, next = "next")
        val info2: LocationsResponse.Info = LocationsResponse.Info(count = 1, pages = 2, prev = "prev", next = null)
        val response = LocationsResponse(info1, listOf(location1))
        val nextResponse = LocationsResponse(info2, listOf(location2))
    }
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        locationsSource = LocationsSource(repository)
    }
    @Test
    fun `locations paging source load - failure - http error`() = runTest {
        val error = RuntimeException("404", Throwable())
        BDDMockito.given(repository.getLocations(BDDMockito.anyInt())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, LocationsResponse.Location>(error)
        assertEquals(
            expectedResult, locationsSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `locations paging source load - failure - received null`() = runTest {
        BDDMockito.given(repository.getLocations(BDDMockito.anyInt())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, LocationsResponse.Location>(NullPointerException("Cannot invoke \"com.nini.flow.locations.LocationsResponse.getInfo()\" because \"itemList\" is null"))
        assertEquals(
            expectedResult.toString(), locationsSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }
    @Test
    fun `locations paging source refresh - success`() = runTest {
        BDDMockito.given(repository.getLocations(BDDMockito.anyInt()))
            .willReturn(response)
        val expectedResult = PagingSource.LoadResult.Page(
            data = response.results ?: emptyList(),
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, locationsSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `locations paging source append - success`() = runTest {
        BDDMockito.given(repository.getLocations(BDDMockito.anyInt()))
            .willReturn(nextResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = nextResponse.results ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, locationsSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `locations paging source prepend - success`() = runTest {
        BDDMockito.given(repository.getLocations(BDDMockito.anyInt()))
            .willReturn(response)
        val expectedResult = PagingSource.LoadResult.Page(
            data = response.results ?: emptyList(),
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, locationsSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}