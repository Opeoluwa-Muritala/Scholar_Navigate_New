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
    ScholarNavigateTheme{
        DatePickerDialog(

        ){

        }
    }

}