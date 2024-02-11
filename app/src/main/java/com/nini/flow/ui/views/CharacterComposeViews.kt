package com.nini.flow.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.nini.flow.ui.theme.FlowTheme
import com.nini.flow.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterItem(
    id:Int,
    imageUrl:String,
    name:String,
    status:String,
    species:String,
    onClick:(id:Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = white,
            ),
            modifier = Modifier.padding(2.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary),
            onClick = {onClick.invoke(id)}
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.character_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .height(80.dp)
                            .width(80.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier,
                        text = name,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        modifier = Modifier,
                        text = species,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    FlowTheme {
        CharacterItem(id = 1, imageUrl = "", name = "", status = "", species = "", onClick = {})
    }
}
