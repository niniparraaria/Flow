package com.nini.flow.core

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.nini.flow.ui.theme.gray
import com.nini.flow.ui.theme.green
import com.nini.flow.ui.theme.red


fun hasInternetConnection(context: Context):Boolean = true
fun String.getColorByStatus():Color{
    return when(this.toLowerCase(Locale.current)){
        "alive" -> green
        "dead" -> red
        else -> gray
    }
}