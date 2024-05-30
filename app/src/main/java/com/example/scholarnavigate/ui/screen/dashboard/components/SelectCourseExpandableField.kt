package com.example.scholarnavigate.ui.screen.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.data.module.Course

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCourseField(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    courses: List<Course>,
    placeholderText: String = "Related to course",
    onCourseClicked: (Course) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    value: String,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier.fillMaxSize(1f).height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val color = Color.Transparent
        ExposedDropdownMenuBox(
            expanded = isOpen,
            onExpandedChange =  onExpandedChange ,

        ) {
            TextField(
                value = value,
                onValueChange = {  },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = color,
                    unfocusedContainerColor = color,
                    unfocusedIndicatorColor = color,
                    focusedIndicatorColor = color,
                    focusedPlaceholderColor = color,
                    unfocusedPlaceholderColor = color
                ),
                placeholder = {
                    Text(text = placeholderText)
                },
                label = {
                    Text(text = "Select Related Course", style = MaterialTheme.typography.bodySmall)
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isOpen)
                }
            )
            ExposedDropdownMenu(
                expanded = isOpen,
                onDismissRequest = onDismissRequest,
                modifier= Modifier.height(130.dp).fillMaxWidth()
            ) {
                courses.forEach { course ->
                    DropdownMenuItem(
                        text = { Text(text = course.name) },
                        onClick = { onCourseClicked(course) }
                    )
                }
            }

        }
    }

}