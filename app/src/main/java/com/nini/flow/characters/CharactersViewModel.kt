package com.nini.flow.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(private val characterSource: CharacterSource):ViewModel() {
    private val _characterState : MutableStateFlow<PagingData<CharactersResponse.Character>> = MutableStateFlow(PagingData.empty())
    var charactersState = _characterState.asStateFlow()
        private set
    init {
        viewModelScope.launch {
            Pager(config = PagingConfig(10, enablePlaceholders = true)) {
                characterSource
            }.flow.cachedIn(viewModelScope).collect {
                _characterState.value = it
            }
        }
    }
}