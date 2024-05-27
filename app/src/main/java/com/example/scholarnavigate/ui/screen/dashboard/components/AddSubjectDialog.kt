package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourseDialog(
    title:String = "Add/Update Course",
    onDissmisRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    isOpen:Boolean
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDissmisRequest,
            title = { Text(text = title) },
            text = {
                Text(text = "Dialog Body")
            },
            confirmButton = {
                TextButton(onClick = onDissmisRequest) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}