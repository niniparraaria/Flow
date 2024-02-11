package com.nini.flow.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nini.flow.core.network.ApiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val charactersRepository: CharactersRepository):ViewModel() {
    private val _characterDetailState : MutableStateFlow<CharacterState> = MutableStateFlow(CharacterState())
    var characterDetailState = _characterDetailState.asStateFlow()
        private set

    fun getCharacter(id:Int){
        viewModelScope.launch {
            charactersRepository.getCharacter(id).collect{
                when(it.status) {
                    ApiStatus.SUCCESS -> {
                        _characterDetailState.value = _characterDetailState.value.copy(status = it.status, it.data)
                    }
                    ApiStatus.ERROR -> {
                        _characterDetailState.value = _characterDetailState.value.copy(status = it.status, messageError = it.message?:"")
                    }
                    else -> {
                        _characterDetailState.value = _characterDetailState.value.copy(status = it.status)
                    }
                }
            }
        }
    }


    data class CharacterState(val status: ApiStatus = ApiStatus.LOADING, val data:CharactersResponse.Character? = null, val messageError:String = "")
}