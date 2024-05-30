package com.example.scholarnavigate.ui.screen.dashboard.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scholarnavigate.courses
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.ui.screen.dashboard.components.SelectCourseField

@Composable
fun SeessionScreen() {
    var isCourseFieldOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var courseFieldText by rememberSaveable {
        mutableStateOf<Course?>(null)
    }


    Scaffold(
        topBar = {
            SessionScreenTopAppBar {

            }
        }
    ) {paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ){
            item {
                TimerSection(modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f))
            }
            item {
                SelectCourseField(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    isOpen = isCourseFieldOpen,
                    courses = courses,
                    onCourseClicked = { course ->
                        courseFieldText = course
                        isCourseFieldOpen = false
                    },
                    onExpandedChange = {isCourseFieldOpen = !isCourseFieldOpen},
                    value = courseFieldText?.name.orEmpty(),
                    onDismissRequest = {isCourseFieldOpen = false}
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreenTopAppBar(
    onBackCLick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
                     IconButton(onClick = onBackCLick) {
                         Icon(
                             imageVector = Icons.Default.ArrowBack,
                             contentDescription = "Navigate Backward"
                         )
                     }
        },
        title = { Text(text = "Study Session")})
}


@Composable
private fun TimerSection(
    modifier: Modifier
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .size(25.dp)
            .border(8.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        ){
            Text(
                text = "00:09:32",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 45.sp)
            )
        }
    }
}

