package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    confirmButtonText: String = "OK",
    dismissButtonText: String = "CANCEL",
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
){
    ScholarNavigateTheme {
        if (isOpen) {
            DatePickerDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    TextButton(onConfirmButtonClick) {
                        Text(text = confirmButtonText)
                    }
                },
                dismissButton = {
                    TextButton(onConfirmButtonClick) {
                        Text(text = dismissButtonText)
                    }

                }
            ) {
                DatePicker(state = state)
            }
        }
    }
}