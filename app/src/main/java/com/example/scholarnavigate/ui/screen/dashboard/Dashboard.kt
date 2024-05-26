package com.example.scholarnavigate.ui.screen.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen() {
    Scaffold (
        topBar = {
            DashboardTopAppBar()
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Scholar Navigate",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}