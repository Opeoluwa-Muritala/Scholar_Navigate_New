package com.example.scholarnavigate.ui.screen.calendar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scholarnavigate.data.util.DateUtils
import com.example.scholarnavigate.data.util.changeMillisToDateString
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.ui.components.SingleRowCalendar
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.viewmodels.CalendarViewModel
import com.example.scholarnavigate.viewmodels.events.CalendarEvents
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalenderUi(navController: NavController) {
    val calendar = Calendar.getInstance(Locale.getDefault())
    var currentDate by rememberSaveable { mutableStateOf(calendar.time) }
    var selectedDate by remember {
        mutableStateOf<Date>(currentDate)
    }
    val viewModel:CalendarViewModel = hiltViewModel()
    val tasks by viewModel.tasks.collectAsState()
    val textColor = if (isSystemInDarkTheme()) White else Blue
    val dateUtils = DateUtils
    val listState = rememberLazyListState()
    val isFABExpanded by remember { derivedStateOf{listState.firstVisibleItemIndex == 0}}
    val date = "${dateUtils.getDayNumber(selectedDate)} ${dateUtils.getMonth3LettersName(selectedDate)} ${dateUtils.getYear(selectedDate)}"


    Scaffold(
        topBar = {
            SingleRowCalendar(
                dayNumTextColor = textColor,
                dayTextColor = textColor,
                iconsTintColor = textColor,
                headTextColor = textColor,
                onSelectedDayChange = { date -> selectedDate = date }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(DashboardScreens.TaskScreen.createTask(0, 0))
                },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task") },
                text = { Text(text = "Add Task") },
                expanded = isFABExpanded
            )
        }
    ) {paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues)
        ){
            todoTasksList(
                "ToDo's For $date" ,
                tasks = tasks.filter { task -> task.dueDates.changeMillisToDateString() ==  date},
                onCheckBoxClick = {task -> viewModel.onEvents(CalendarEvents.OnTaskIsCompleteChange(task))},
                onTaskCardCLick = {task ->
                    navController.navigate(DashboardScreens.TaskScreen.createTask(
                    task.taskId ?: 0,
                    task.taskSubjectId
                ))},

                )
        }
    }
}