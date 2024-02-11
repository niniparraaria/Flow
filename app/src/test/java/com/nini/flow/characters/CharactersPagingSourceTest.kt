package com.nini.flow.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.nini.flow.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CharactersPagingSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var repository: CharactersRepository
    lateinit var charactersPagingSource: CharacterSource
    companion object {
        val origins = CharactersResponse.Origin("name1", "url1")
        val locations =  CharactersResponse.Location("name2", "url2")
        val episodes = listOf( "url1", "url2")
        val character1 = CharactersResponse.Character(  id = 1, name = "character 1", status = "alive", species = "species 1", type = "type1", gender = "gender 1", origin = origins, location = locations, image = "url1", episode = episodes, url = "url1", created = "date")
        val character2 = CharactersResponse.Character(  id = 2, name = "character 2", status = "dead", species = "species 2", type = "type2", gender = "gender 2", origin = origins, location = locations, image = "url2", episode = episodes, url = "url2", created = "date2")
        val info1: CharactersResponse.Info = CharactersResponse.Info(count = 1, pages = 2, prev = null, next = "next")
        val info2: CharactersResponse.Info = CharactersResponse.Info(count = 1, pages = 2, prev = "prev", next = null)
        val charactersResponse =CharactersResponse(info1, listOf(character1))
        val nextCharactersResponse =CharactersResponse(info2, listOf(character2))
    }
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        charactersPagingSource = CharacterSource(repository)
    }
    @Test
    fun `characters paging source load - failure - http error`() = runTest {
        val error = RuntimeException("404", Throwable())
        given(repository.getCharacters(anyInt())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, CharactersResponse.Character>(error)
        assertEquals(
            expectedResult, charactersPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `characters paging source load - failure - received null`() = runTest {
        given(repository.getCharacters(anyInt())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, CharactersResponse.Character>(NullPointerException("Cannot invoke \"com.nini.flow.characters.CharactersResponse.getInfo()\" because \"characterList\" is null"))
        assertEquals(
            expectedResult.toString(), charactersPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }
    @Test
    fun `characters paging source refresh - success`() = runTest {
        given(repository.getCharacters(anyInt())).willReturn(charactersResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = charactersResponse.results ?: emptyList(),
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, charactersPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `characters paging source append - success`() = runTest {
        given(repository.getCharacters(anyInt())).willReturn(nextCharactersResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = nextCharactersResponse.results ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, charactersPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `characters paging source prepend - success`() = runTest {
        given(repository.getCharacters(anyInt())).willReturn(charactersResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = charactersResponse.results ?: emptyList(),
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, charactersPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}
