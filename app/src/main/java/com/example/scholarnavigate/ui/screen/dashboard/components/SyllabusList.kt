package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.R


fun LazyListScope.SyllabusList(
    syllabus: String
) {
    val syllabusList: List<String> = syllabus.split("\n")
    item {
        Text(
            text = "Syllabus",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
        if (syllabus.isEmpty()){
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.task_icon),
                    contentDescription = "No Syllabus"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No Syllabus",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    items(syllabusList){
        if (syllabus.isEmpty()){}
        else { SyllabusItem(syllabus = syllabus)}
    }
}


@Composable
private fun SyllabusItem(syllabus: String) {
    ElevatedCard {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.outline_panorama_photosphere_select_24),
                contentDescription = "Syllabus Item"
            )
            Text(
                text = syllabus,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}