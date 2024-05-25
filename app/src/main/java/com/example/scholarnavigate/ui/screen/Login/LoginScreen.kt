package com.example.scholarnavigate.ui.screen.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.scholarnavigate.ui.screen.Login.components.GoogleSignIn

@Composable
fun LoginUi(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleSignIn(
            onClick = onLoginClick
        )
    }
}