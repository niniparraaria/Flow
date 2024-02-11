package com.nini.flow.characters

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nini.flow.CoroutineTestRule
import com.nini.flow.core.network.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CharactersRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource
    @Mock
    private lateinit var context: Context
    private lateinit var repository: CharactersRepository

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repository = CharactersRepository(remoteDataSource, context)
    }

    @Test
    fun `characters results empty`() = runTest {
        val info: CharactersResponse.Info = CharactersResponse.Info(count = 0, pages = 0, prev = null, next = null)
        val expectedCharactersResponse =CharactersResponse(info, listOf())
        `when`(remoteDataSource.getCharacters(anyInt())).thenReturn(expectedCharactersResponse)
        val result = repository.getCharacters(1)
        Mockito.verify(remoteDataSource).getCharacters(1)
        Assert.assertEquals(expectedCharactersResponse, result)
    }
    @Test
    fun `characters results with data`() = runTest {
        val info: CharactersResponse.Info = CharactersResponse.Info(count = 1, pages = 1, prev = null, next = null)
        val origins = CharactersResponse.Origin("name1", "url1")
        val locations =  CharactersResponse.Location("name2", "url2")
        val episodes = listOf( "url1", "url2")
        val character = CharactersResponse.Character(  id = 1, name = "character 1", status = "alive", species = "species 1", type = "type1", gender = "gender 1", origin = origins, location = locations, image = "url1", episode = episodes, url = "url1", created = "date")
        val expectedCharactersResponse =CharactersResponse(info, listOf(character))
        `when`(remoteDataSource.getCharacters(anyInt())).thenReturn(expectedCharactersResponse)
        val result = repository.getCharacters(1)
        Mockito.verify(remoteDataSource).getCharacters(1)
        Assert.assertEquals(expectedCharactersResponse, result)
    }
    @Test
    fun `characters results with data null`() = runTest {
        val info: CharactersResponse.Info = CharactersResponse.Info(count = 1, pages = 1, prev = null, next = null)
        val expectedCharactersResponse =CharactersResponse(info, null)
        `when`(remoteDataSource.getCharacters(anyInt())).thenReturn(expectedCharactersResponse)
        val result = repository.getCharacters(1)
        Mockito.verify(remoteDataSource).getCharacters(1)
        Assert.assertEquals(expectedCharactersResponse, result)
    }
}