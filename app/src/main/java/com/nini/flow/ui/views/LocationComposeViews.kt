package com.nini.flow.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
fun LocationItem(
    name:String,
    dimension:String,
    type:String
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
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = name,
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Justify
                    )
                )
                Text(
                    modifier = Modifier,
                    text = type,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Justify
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.location_item_dimension, dimension),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Justify
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationsPreview() {
    FlowTheme {
        LocationItem(name = "test", type = "test", dimension = "test")
    }
}
