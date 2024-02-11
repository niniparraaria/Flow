package com.nini.flow.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EpisodesViewModel(private val source: EpisodesSource):ViewModel() {
    private val _episodesState : MutableStateFlow<PagingData<EpisodesResponse.Episode>> = MutableStateFlow(PagingData.empty())
    var episodesState = _episodesState.asStateFlow()
        private set
    init {
        viewModelScope.launch {
            Pager(config = PagingConfig(10, enablePlaceholders = true)) {
                source
            }.flow.cachedIn(viewModelScope).collect {
                _episodesState.value = it
            }
        }
    }
}