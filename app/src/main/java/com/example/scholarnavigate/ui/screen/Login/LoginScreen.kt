package com.example.scholarnavigate.ui.screen.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.R
import com.example.scholarnavigate.ui.screen.Login.components.GoogleSignIn
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme

@Composable
fun LoginUi(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    ScholarNavigateTheme {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.applogo),
                        contentDescription = "Scholar Navigate",
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    GoogleSignIn(
                        onClick = onLoginClick
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

            }
        }
    }

}