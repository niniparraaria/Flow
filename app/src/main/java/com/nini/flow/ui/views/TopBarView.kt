package com.nini.flow.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nini.flow.R
import com.nini.flow.ui.theme.FlowTheme
import com.nini.flow.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title:String,onClick:() -> Unit ){
    TopAppBar(
        title = { Text(text = title, fontSize = 24.sp ) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = white),
        navigationIcon = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = white,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clickable {
                        onClick.invoke()
                    },
                contentDescription = stringResource(id = R.string.error_description)
            )
        }
    )
}

@Preview
@Composable
fun TopBarPreview(){
    FlowTheme {
        TopBar(title = "titulo", onClick = {})
    }
}