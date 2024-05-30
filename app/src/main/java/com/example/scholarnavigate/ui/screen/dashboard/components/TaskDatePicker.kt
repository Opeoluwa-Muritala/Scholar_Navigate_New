package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

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
                modifier = Modifier.padding(12.dp),
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
                DatePicker(
                    state = state,
                    dateValidator = { timestamp ->
                        val selectedDate = Instant
                            .ofEpochMilli(timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val currentDate = LocalDate.now(ZoneId.systemDefault())
                        selectedDate >= currentDate
                    }
                )
            }
        }
    }
}