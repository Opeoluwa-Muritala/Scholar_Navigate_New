package com.example.scholarnavigate.ui.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scholarnavigate.viewmodels.CalendarViewModel
import com.mrerror.singleRowCalendar.SingleRowCalendar

@Composable
fun CalenderUi() {
    var selectedDate by remember {
        mutableStateOf<Any>("")
    }
    val viewModel:CalendarViewModel = hiltViewModel()
    
    Scaffold(
        topBar = {
                SingleRowCalendar(onSelectedDayChange = { date ->selectedDate = date})
        }
    ) {
        Column(
            Modifier.padding(it)
        ){
            Text(text = "Selected Date: $selectedDate")
        }
    }
}