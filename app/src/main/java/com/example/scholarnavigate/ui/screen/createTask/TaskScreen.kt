package com.example.scholarnavigate.ui.screen.createTask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.ui.screen.dashboard.components.TaskCheckBox
import com.example.scholarnavigate.ui.theme.Red
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme

@Composable
fun TaskScreen() {
    ScholarNavigateTheme {
        Scaffold(
            topBar = {
                TaskScreenTopAppBar(
                    isTaskExist = true,
                    isTaskComplete = false,
                    checkBoxBorderColor = Red,
                    onBackButtonClick = { /*TODO*/ },
                    onDeleteButtonClick = { /*TODO*/ },
                    onCheckBoxClick = {}
                    )
            }
        ) {paddingValues ->
            Column(
                Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 12.dp)
            ) {

            }


        }
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
                         IconButton(onClick = { /*TODO*/ }) {
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
                    onCheckBoxClick = {}
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    )
}