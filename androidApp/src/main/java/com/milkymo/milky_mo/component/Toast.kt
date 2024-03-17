package com.milkymo.milky_mo.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun Toast(text: String) {
    val context = LocalContext.current
    android.widget.Toast.makeText(
        context, text, android.widget.Toast.LENGTH_LONG
    ).show()
}
