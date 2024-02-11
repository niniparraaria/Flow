package com.nini.flow.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nini.flow.R
import com.nini.flow.ui.theme.FlowTheme
import com.nini.flow.ui.theme.white

@Composable
fun ErrorScreen(message:String, onClick:() -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = white,
                ),
                modifier = Modifier.padding(2.dp),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error),
            ) {
                Icon(
                    Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    contentDescription = stringResource(id = R.string.error_description)
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Justify
                )
            )
        }

        OutlinedButton(onClick = { onClick.invoke() },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error)
        ) {
            Text(
                color = MaterialTheme.colorScheme.error,
                text = stringResource(id = R.string.try_again_label),
            )
        }
    }
}

@Composable
fun ErrorItem(message:String, onClick:() -> Unit){
    Column(
        modifier = Modifier.background(white).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = message,
            style = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        )
        OutlinedButton(onClick = { onClick.invoke() },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error)
        ) {
            Text(
                color = MaterialTheme.colorScheme.error,
                text = stringResource(id = R.string.try_again_label),
            )
        }
    }
}

@Preview
@Composable
fun ErrorPreview(){
    FlowTheme {
        ErrorItem("Error", onClick = {})
    }
}