package com.nini.flow.characters

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nini.flow.R
import com.nini.flow.core.getColorByStatus
import com.nini.flow.core.network.ApiStatus
import com.nini.flow.ui.theme.FlowTheme
import com.nini.flow.ui.theme.white
import com.nini.flow.ui.views.ErrorScreen
import com.nini.flow.ui.views.LoadingScreen
import com.nini.flow.ui.views.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterDetailScreen(id:Int, onBack:() -> Unit){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
    ) {
        val characterViewModel: CharacterDetailViewModel = koinViewModel()
        val state = characterViewModel.characterDetailState.collectAsState()
        LaunchedEffect(Unit, block = {
            characterViewModel.getCharacter(id)
        })
        when(state.value.status) {
            ApiStatus.LOADING -> {
                LoadingScreen()
            }

            ApiStatus.ERROR -> {
                ErrorScreen(message = state.value.messageError) {
                    characterViewModel.getCharacter(id)
                }
            }

            ApiStatus.SUCCESS -> {
                val data = state.value.data!!
                val status = data.status
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    TopBar(title = stringResource(id = R.string.detail_label)) {
                        onBack.invoke()
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.character_description),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            text = data.name,
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center
                            )
                        )
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = white,
                            ),
                            border = BorderStroke(1.dp, color =  status.getColorByStatus()),
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.Top)
                                .padding(end = 16.dp, top = 16.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = status,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = status.getColorByStatus(),
                                    textAlign = TextAlign.Right
                                )
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp)
                            .fillMaxWidth(),
                        text = data.species,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Justify
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp)
                            .fillMaxWidth(),
                        text = data.gender,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Justify
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 14.dp, top = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.character_item_origin, data.origin.name),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Justify
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 14.dp, top = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.character_item_location, data.location.name),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Justify
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterDetailScreenPreview(){
    FlowTheme {
        CharacterDetailScreen(0,onBack = {})
    }
}