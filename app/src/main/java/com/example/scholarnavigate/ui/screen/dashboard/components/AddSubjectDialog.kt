package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.ui.theme.primaryDark
import com.example.scholarnavigate.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourseDialog(
    title:String = "Add/Update Course",
    onDissmisRequest: () -> Unit,
    courseName: String,
    studyHours: String,
    syllabus: String,
    onSyllabusChange: (String) -> Unit,
    oncourseNameChange: (String) -> Unit,
    onStudyHoursChange: (String) -> Unit,
    onConfirmButtonClick: () -> Unit,
    isOpen:Boolean,
    selectedColors: List<Color>,
    onColorChange: (List<Color>) -> Unit
) {
    var courseNameError by remember {
        mutableStateOf<String?>(null)
    }
    var studyHoursError by remember {
        mutableStateOf<String?>(null)
    }
    var syllabusError by remember {
        mutableStateOf<String?>(null)
    }
    courseNameError = when {
        courseName.isBlank() -> "Please enter a course name."
        courseName.length < 2 -> "Course name is too short."
        courseName.length > 20 -> "Course name is too long."
        else -> null
    }

    syllabusError = when {
        syllabus.isBlank() -> "Please enter Your Syllabus"
        courseName.length < 2 -> "End every line with period '.'"
        courseName.length < 30 -> "Course name is too long."
        else -> null
    }

    studyHoursError = when {
        studyHours.isBlank() -> "Please enter goal study hours."
        studyHours.toFloatOrNull() == null -> "Invalid Number"
        studyHours.toFloat() < 1f -> "Please set a least 1 hour."
        studyHours.toFloat() > 1000f -> "Please set a maximum of 1000 hour."
        else -> null
    }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDissmisRequest,
            title = { Text(text = title) },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                    ) {
                        Course.subjectCardColors.forEach{ colors ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(brush = Brush.verticalGradient(colors))
                                    .border(
                                        width = 3.dp,
                                        color = if (colors == selectedColors) primaryLight
                                        else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { onColorChange(colors) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = courseName,
                        onValueChange = oncourseNameChange,
                        label = { Text(text = "Course Name")},
                        singleLine = true,
                        isError = courseNameError != null && courseName.isNotBlank(),
                        supportingText = { Text(text = courseNameError.orEmpty())}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = studyHours,
                        onValueChange = onStudyHoursChange,
                        label = { Text(text = "Goal Number Hours")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = studyHoursError != null && studyHours.isNotBlank(),
                        supportingText = { Text(text = studyHoursError.orEmpty())}
                    )
                    OutlinedTextField(
                        value = syllabus.replace(".", ".\n"),
                        onValueChange = onSyllabusChange,
                        label = { Text(text = "Syllabus")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = syllabusError != null && syllabus.isNotBlank(),
                        supportingText = { Text(text = syllabusError.orEmpty())}
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled = studyHoursError == null && courseNameError == null && syllabusError  == null
                ) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDissmisRequest,
                    enabled = studyHoursError == null && courseNameError == null && syllabusError  == null
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}