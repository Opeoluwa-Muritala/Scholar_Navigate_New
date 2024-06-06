package com.example.scholarnavigate.ui.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mrerror.singleRowCalendar.SingleRowCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderUi() {
    var selectedDate by remember {
        mutableStateOf<Any>("")
    }
    
    Scaffold(
        topBar = {
            LargeTopAppBar(title = {
                SingleRowCalendar(onSelectedDayChange = {selectedDate = it})
            })
        }
    ) {
        Column(
            Modifier.padding(it)
        ){
            Text(text = "Selected Date: $selectedDate")
        }
    }
}