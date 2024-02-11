package com.nini.flow.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nini.flow.CoroutineTestRule
import com.nini.flow.core.network.ApiStatus
import com.nini.flow.core.network.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var mockRepository: CharactersRepository

    private lateinit var viewModel: CharacterDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = CharacterDetailViewModel(mockRepository)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runTest {
            val origins = CharactersResponse.Origin("name1", "url1")
            val locations =  CharactersResponse.Location("name2", "url2")
            val episodes = listOf( "url1", "url2")
            val character = CharactersResponse.Character(  id = 1, name = "character 1", status = "alive", species = "species 1", type = "type1", gender = "gender 1", origin = origins, location = locations, image = "url1", episode = episodes, url = "url1", created = "date")

            val networkResultExpected = NetworkResult.Success(character)
            val stateExpected = CharacterDetailViewModel.CharacterState(ApiStatus.SUCCESS, data = character)
            val stateLoadingExpected = CharacterDetailViewModel.CharacterState(ApiStatus.LOADING)
            val flow = flow{
                emit(networkResultExpected)
            }

            given(mockRepository.getCharacter(anyInt())).willReturn(flow)
            viewModel.getCharacter(1)
            viewModel.characterDetailState.test {
                assertEquals(
                    stateLoadingExpected,
                    awaitItem()
                )
                assertEquals(
                    stateExpected,
                    awaitItem()
                )
            }
            verify(mockRepository).getCharacter(anyInt())
        }
    }
    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        runTest{
            val errorMessage = "Error Message For You"
            val networkResultExpected = NetworkResult.Error<CharactersResponse.Character>(IllegalStateException(errorMessage).toString())
            val stateExpected = CharacterDetailViewModel.CharacterState(ApiStatus.ERROR, messageError = IllegalStateException(errorMessage).toString())
            val stateLoadingExpected = CharacterDetailViewModel.CharacterState(ApiStatus.LOADING)
            val flow = flow{
                emit(networkResultExpected)
            }
            given(mockRepository.getCharacter(anyInt())).willReturn(flow)
            viewModel.getCharacter(1)
            viewModel.characterDetailState.test {
                assertEquals(
                    stateLoadingExpected,
                    awaitItem()
                )
                assertEquals(
                    stateExpected,
                    awaitItem()
                )
            }
            verify(mockRepository).getCharacter(anyInt())
        }
    }
}