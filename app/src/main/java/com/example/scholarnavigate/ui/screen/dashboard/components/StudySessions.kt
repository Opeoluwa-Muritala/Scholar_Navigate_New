package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.util.changeMillisToDateString
import com.example.scholarnavigate.data.util.toHours
import com.example.scholarnavigate.ui.theme.onPrimaryDark
import com.example.scholarnavigate.ui.theme.onPrimaryLight
import com.example.scholarnavigate.ui.theme.primaryContainerDark
import com.example.scholarnavigate.ui.theme.primaryContainerLight

fun LazyListScope.studySessionsList(
    sectionTitle: String,
    sessions: List<Session>,
    emptySessionList: String = "You don't have any recent study session \n " +
            "Start a study session to begin recording your progress",
    onDeleteClick: (Session) -> Unit

){
    item {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
        if (sessions.isEmpty()){
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.session_icon),
                    contentDescription = emptySessionList
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emptySessionList,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    items(sessions){session ->
        StudySessionCard(
            session = session,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            onDeleteClick = {onDeleteClick(session)}
        )

    }
}


@Composable
private fun StudySessionCard(
    modifier: Modifier = Modifier,
    session: Session,
    onDeleteClick: () -> Unit

) {
    Card(
        modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(20.dp))
            Column(
                Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = session.relatedToSubect,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = session.date.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodySmall,
                )
                }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${session.duration.toHours()} hr",
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Session",
                )
            }
        }
    }
}