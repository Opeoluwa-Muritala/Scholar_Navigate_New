package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.data.util.Priority
import com.example.scholarnavigate.ui.theme.onPrimaryDark
import com.example.scholarnavigate.ui.theme.onPrimaryLight
import com.example.scholarnavigate.ui.theme.primaryContainerDark
import com.example.scholarnavigate.ui.theme.primaryContainerLight

fun LazyListScope.todoTasksList(
    sectionTitle: String,
    tasks: List<Task>,
    emptyTaskList: String = "You don't have any upcoming task. \n Click the + button in the subject screen to add new task",
    onTaskCardCLick: (Task) -> Unit,
    onCheckBoxClick: (Int?) -> Unit
){
    item {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
        if (tasks.isEmpty()){
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.tasks),
                    contentDescription = emptyTaskList
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emptyTaskList,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    items(tasks){task ->
        TaskCard(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            task = task,
            onCheckBoxClick = {onCheckBoxClick(task.priority)},
            onClick = {onTaskCardCLick(task)}
        )

    }
}


@Composable
private fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckBoxClick: () -> Unit,
    onClick: () -> Unit
) {

    ElevatedCard(
        modifier.clickable { 
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TaskCheckBox(
                isComplete = task.isComplete,
                borderColor = Priority.fromInt(task.priority).color,
                onCheckBoxClick = onCheckBoxClick
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isComplete){
                        TextDecoration.LineThrough
                    } else TextDecoration.None,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.dueDates}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}