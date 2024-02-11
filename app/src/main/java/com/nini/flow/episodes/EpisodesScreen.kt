package com.nini.flow.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nini.flow.R
import com.nini.flow.ui.theme.FlowTheme
import com.nini.flow.ui.theme.white
import com.nini.flow.ui.views.EpisodeItem
import com.nini.flow.ui.views.ErrorItem
import com.nini.flow.ui.views.LoadingScreen
import com.nini.flow.ui.views.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun EpisodeScreen(onBack:() -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
    ) {
        val episodesViewModel: EpisodesViewModel = koinViewModel()
        val response = episodesViewModel.episodesState.collectAsLazyPagingItems()
        val loadState = response.loadState
        Column {
            TopBar(title = stringResource(id = R.string.episodes_label)) {
                onBack.invoke()
            }
            LazyColumn{
                items(response.itemCount){
                    val data = response[it]!!
                    EpisodeItem(name = data.name, date = data.airDate?:"")

                }
                response.apply {
                    when {
                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                            item {
                                LoadingScreen(modifier = Modifier
                                    .fillMaxWidth()
                                    .background(white)
                                    .padding(16.dp))
                            }
                        }

                        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                            item {
                                ErrorItem(message = stringResource(id = R.string.try_again_message)) {
                                    response.retry()
                                }
                            }
                        }

                        loadState.refresh is LoadState.NotLoading -> {
                        }
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun EpisodePreview() {
    FlowTheme {
        EpisodeScreen(onBack = {})
    }
}