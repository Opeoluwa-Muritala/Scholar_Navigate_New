package com.example.scholarnavigate.ui.screen.dashboard.components

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