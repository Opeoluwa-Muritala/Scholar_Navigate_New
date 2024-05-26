package com.example.scholarnavigate.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scholarnavigate.ui.theme.onPrimaryDark
import com.example.scholarnavigate.ui.theme.onPrimaryLight
import com.example.scholarnavigate.ui.theme.primaryContainerDark
import com.example.scholarnavigate.ui.theme.primaryContainerLight
import com.example.scholarnavigate.ui.theme.primaryDark
import com.example.scholarnavigate.ui.theme.primaryLight

@Composable
fun CountCard(
    modifier: Modifier = Modifier,
    headingText: String,
    count: String
) {
    val textColors = if (isSystemInDarkTheme()) onPrimaryDark else onPrimaryLight
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isSystemInDarkTheme()) primaryContainerLight else primaryContainerDark
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 4.dp,
                    vertical = 12.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = headingText,
                style = MaterialTheme.typography.labelSmall,
                color = textColors
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = count,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 30.sp),
                color = textColors
            )

        }
    }
}