package com.example.scholarnavigate.ui.screen.calendar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scholarnavigate.tasks
import com.example.scholarnavigate.ui.components.SingleRowCalendar
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.viewmodels.CalendarViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalenderUi() {
    val calendar = Calendar.getInstance(Locale.getDefault())

    var currentDate by rememberSaveable { mutableStateOf(calendar.time) }

    var selectedDate by remember {
        mutableStateOf<Date>(currentDate)
    }
    val viewModel:CalendarViewModel = hiltViewModel()

    val textColor = if (isSystemInDarkTheme()) White else Blue
    Scaffold(
        topBar = {
            SingleRowCalendar(
                dayNumTextColor = textColor,
                dayTextColor = textColor,
                iconsTintColor = textColor,
                headTextColor = textColor,
                onSelectedDayChange = { date -> selectedDate = date }
            )
        }
    ) {
        LazyColumn(
            Modifier.padding(it)
        ){
            todoTasksList(
                "ToDo's For ${selectedDate.date} ${selectedDate.month} ${selectedDate.year}" ,
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardCLick = {}
            )
        }
    }
}