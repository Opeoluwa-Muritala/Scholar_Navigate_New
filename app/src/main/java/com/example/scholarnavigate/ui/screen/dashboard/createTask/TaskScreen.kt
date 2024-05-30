package com.example.scholarnavigate.ui.screen.dashboard.createTask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.courses
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.util.Priority
import com.example.scholarnavigate.data.util.changeMillisToDateString
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SelectCourseField
import com.example.scholarnavigate.ui.screen.dashboard.components.TaskCheckBox
import com.example.scholarnavigate.ui.screen.dashboard.components.TaskDatePicker
import com.example.scholarnavigate.ui.theme.Red
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import java.time.Instant

@Composable
fun TaskScreenNavigation(){
    TaskScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen() {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var taskTitleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    taskTitleError = when {
        title.isBlank() -> "Please enter the task title"
        title.length < 4 -> "Task title is too short"
        title.length > 20 -> "Task title is too long"
        else -> null
    }
    var isDeleteTask by remember {
        mutableStateOf(false)
    }
    var isDatePickerDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var isCourseFieldOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var courseFieldText by rememberSaveable {
        mutableStateOf<Course?>(null)
    }



    ScholarNavigateTheme {


        DeleteCourseDialog(
            title = "Delete Task",
            onDissmisRequest = { isDeleteTask = false },
            body = "Are you sure you want to delete this Task" +
                    ". This action can not be undone.",
            onConfirmButtonClick = { isDeleteTask = false },
            isOpen = isDeleteTask
        )


        TaskDatePicker(
            state = datePickerState,
            isOpen = isDatePickerDialogOpen,
            onDismissRequest = { isDatePickerDialogOpen = false },
            onConfirmButtonClick = { isDatePickerDialogOpen = false}
        )

        Scaffold(
            topBar = {
                TaskScreenTopAppBar(
                    isTaskExist = true,
                    isTaskComplete = false,
                    checkBoxBorderColor = Red,
                    onBackButtonClick = { /*TODO*/ },
                    onDeleteButtonClick = { isDeleteTask = true },
                    onCheckBoxClick = {}
                    )
            }
        ) {paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it},
                    label = { Text(text = "Title")},
                    singleLine = true,
                    isError = taskTitleError != null,
                    supportingText = {
                        Text(text = taskTitleError.orEmpty())
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = {description = it},
                    label = { Text(text = "Description")},
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Due Date", style = MaterialTheme.typography.bodySmall)
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = datePickerState.selectedDateMillis.changeMillisToDateString(),
                        style = MaterialTheme.typography.bodyLarge)
                    IconButton(onClick = { isDatePickerDialogOpen = true }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Due Date")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Priority", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth()) {
                    Priority.entries.forEach{priority ->
                        PriorityButton(
                            modifier = Modifier.weight(1f),
                            label = priority.title,
                            backgroundColor = priority.color,
                            borderColor = if (priority == Priority.MEDIUM) Color.White else Color.Transparent,
                            labelColor = if (priority == Priority.MEDIUM) Color.White else Color.White.copy(0.75f),
                            onClick = {}
                            )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                SelectCourseField(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    isOpen = isCourseFieldOpen,
                    courses = courses,
                    onCourseClicked = { course ->
                                      courseFieldText = course
                        isCourseFieldOpen = false
                    },
                    onExpandedChange = {isCourseFieldOpen = !isCourseFieldOpen},
                    value = courseFieldText?.name.orEmpty(),
                    onDismissRequest = {isCourseFieldOpen = false}
                )
                Box(modifier = Modifier.size(30.dp))
                Button(
                    enabled = taskTitleError == null,
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PriorityButton(
    modifier: Modifier =Modifier,
    label:String,
    backgroundColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit
){
    Box(modifier = modifier
        .background(backgroundColor)
        .padding(5.dp)
        .border(2.dp, borderColor, RoundedCornerShape(5.dp))
        .padding(5.dp)
        .clickable { onClick() },
        contentAlignment = Alignment.Center
    )
    {
        Text(text = label, color = labelColor)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopAppBar(
    isTaskExist: Boolean,
    isTaskComplete: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit
    ) {
    TopAppBar(
        title = { Text(text = "Create Task") },
        navigationIcon = {
                         IconButton(onClick = onBackButtonClick) {
                             Icon(
                                 imageVector = Icons.Default.ArrowBack,
                                 contentDescription = "Navigate Back"
                             )
                         }
        },
        actions = {
            if (isTaskExist){
                TaskCheckBox(
                    isComplete = isTaskComplete,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onCheckBoxClick
                )
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    )

}