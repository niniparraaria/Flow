package com.nini.flow.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationsViewModel(private val source: LocationsSource):ViewModel() {
    private val _locationsState : MutableStateFlow<PagingData<LocationsResponse.Location>> = MutableStateFlow(PagingData.empty())
    var locationsState = _locationsState.asStateFlow()
        private set
    init {
        viewModelScope.launch {
            Pager(config = PagingConfig(10, enablePlaceholders = true)) {
                source
            }.flow.cachedIn(viewModelScope).collect {
                _locationsState.value = it
            }
        }
    }
}