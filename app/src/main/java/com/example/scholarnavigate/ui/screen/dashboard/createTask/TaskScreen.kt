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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scholarnavigate.courses
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.uimodule.TaskState
import com.example.scholarnavigate.data.util.Priority
import com.example.scholarnavigate.data.util.changeMillisToDateString
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SelectCourseField
import com.example.scholarnavigate.ui.screen.dashboard.components.TaskCheckBox
import com.example.scholarnavigate.ui.screen.dashboard.components.TaskDatePicker
import com.example.scholarnavigate.ui.theme.Red
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.viewmodels.TaskViewModel
import com.example.scholarnavigate.viewmodels.events.TaskEvent
import java.time.Instant

@Composable
fun TaskScreenNavigation(navController: NavController){
    val viewModel: TaskViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    TaskScreen(
        state = state,
        onEvent = viewModel::OnEvent,
        onBackButtonClick = {
            navController.navigateUp()
        },
        onSaveButtonClick = {
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    onBackButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    var taskTitleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    taskTitleError = when {
        state.title.isBlank() -> "Please enter the task title"
        state.title.length < 4 -> "Task title is too short"
        state.title.length > 20 -> "Task title is too long"
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
            onConfirmButtonClick = {
                onEvent(TaskEvent.DeleteTask)
                isDeleteTask = false },
            isOpen = isDeleteTask
        )


        TaskDatePicker(
            state = datePickerState,
            isOpen = isDatePickerDialogOpen,
            onDismissRequest = { isDatePickerDialogOpen = false },
            onConfirmButtonClick = {
                onEvent(TaskEvent.OnDateChange(datePickerState.selectedDateMillis))
                isDatePickerDialogOpen = false}
        )

        Scaffold(
            topBar = {
                TaskScreenTopAppBar(
                    isTaskExist = state.currentTaskId != null,
                    isTaskComplete = state.isTaskComplete,
                    checkBoxBorderColor = Red,
                    onBackButtonClick = onBackButtonClick,
                    onDeleteButtonClick = {
                        onEvent(TaskEvent.DeleteTask)
                        isDeleteTask = true },
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
                    value = state.title,
                    onValueChange = { onEvent(TaskEvent.OnTitleChange(it))},
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
                    value = state.description,
                    onValueChange = {onEvent(TaskEvent.OnDescriptionChange(it))},
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
                        text = state.dueDate.changeMillisToDateString(),
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
                            borderColor = if (priority == state.priority) Color.White else Color.Transparent,
                            labelColor = if (priority == state.priority) Color.White else Color.White.copy(0.75f),
                            onClick = {
                                onEvent(TaskEvent.OnPriorityChange(priority))}
                            )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                SelectCourseField(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    isOpen = isCourseFieldOpen,
                    courses = state.subjects,
                    onCourseClicked = { course ->
                                      onEvent(TaskEvent.OnRelatedCourseSelect(course))
                        isCourseFieldOpen = false
                    },
                    onExpandedChange = {isCourseFieldOpen = !isCourseFieldOpen},
                    value = state.relatedToSubject ?: "",
                    onDismissRequest = {isCourseFieldOpen = false}
                )
                Box(modifier = Modifier.size(30.dp))
                Button(
                    enabled = taskTitleError == null,
                    onClick = {
                        onEvent(TaskEvent.SaveTask)
                        onSaveButtonClick()
                              },
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