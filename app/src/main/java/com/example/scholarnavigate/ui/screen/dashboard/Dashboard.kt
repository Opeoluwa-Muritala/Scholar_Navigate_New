package com.example.scholarnavigate.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.ui.components.CountCard
import com.example.scholarnavigate.ui.screen.dashboard.components.AddCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SubjectCard
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.ui.theme.onPrimaryDark
import com.example.scholarnavigate.ui.theme.onPrimaryLight
import com.example.scholarnavigate.ui.theme.primaryDark
import com.example.scholarnavigate.ui.theme.primaryLight

@Composable
fun DashboardScreen() {

    val courses = listOf(
        Course("MAT 412", 2f, Course.subjectCardColors[0], 0),
        Course("CHM 404", 10f, Course.subjectCardColors[1], 0),
        Course("PHY 402", 6f, Course.subjectCardColors[2], 0),
        Course("STA 410", 8f, Course.subjectCardColors[3], 0),
        Course("GNS 424", 2f, Course.subjectCardColors[4], 0),
    )

    val tasks = listOf(
        Task(
            "Prepare Notes",
            "",
            0L,
            1,
            relatedCourses = "",
            isComplete = false,
            taskId = 0,
            taskSubjectId = 0
        ),
        Task(
            "Go to Lecture",
            "",
            0L,
            2,
            relatedCourses = "",
            isComplete = true,
            taskId = 0,
            taskSubjectId = 0
        ),
        Task(
            "Read The Lecturer Material On Mat",
            "",
            10L,
            0,
            relatedCourses = "",
            isComplete = false,
            taskId = 0,
            taskSubjectId = 0
        ),
        Task(
            "Go For Tutorials",
            "",
            10L,
            2,
            relatedCourses = "",
            isComplete = true,
            taskId = 0,
            taskSubjectId = 0
        ),
        Task(
            "Submit Assignments",
            "",
            10L,
            1,
            relatedCourses = "",
            isComplete = false,
            taskId = 0,
            taskSubjectId = 0
        ),
        Task(
            "Lorem Ipsum LaLam hardy jhg  retread g druthers k g utf ggmap dtd,h,gkg after ej " +
                    "mg,sited proj gkf",
            "",
            10L,
            1,
            relatedCourses = "",
            isComplete = false,
            taskId = 0,
            taskSubjectId = 0
        )

    )

    val sessions = listOf(
        Session(
            sessionSubjectId = 0,
            relatedToSubect = "CHE",
            date = 0L,
            duration = 2L,
            sessionId = 0
        ),
        Session(
            sessionSubjectId = 0,
            relatedToSubect = "MAT",
            date = 0L,
            duration = 2L,
            sessionId = 0
        ),
        Session(
            sessionSubjectId = 0,
            relatedToSubect = "PHY",
            date = 0L,
            duration = 2L,
            sessionId = 0
        ),
        Session(
            sessionSubjectId = 0,
            relatedToSubect = "CSC",
            date = 0L,
            duration = 2L,
            sessionId = 0
        )
    )

    var isAddSubjectDialogOpen by remember { mutableStateOf(false) }
    AddCourseDialog(
        onDissmisRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = { isAddSubjectDialogOpen = false },
        isOpen = isAddSubjectDialogOpen
    )


    ScholarNavigateTheme {
        Scaffold (
            topBar = {
                DashboardTopAppBar()
            },
            containerColor = if (isSystemInDarkTheme()) primaryLight else primaryDark
        ){ paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                item {
                    CountCardSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        subjectCount = 5,
                        studiedHours = "15",
                        goalHours = "17"
                    )
                }
                item {
                    SubjectCardSection(
                        modifier = Modifier.fillMaxWidth(),
                        courseList = emptyList(),
                        onAddCourseClick = {
                            isAddSubjectDialogOpen = true
                        }
                    )
                }
                item {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 20.dp)
                    ) {
                        Text(text = "Start Study Session")
                    }
                }
                todoTasksList(
                    sectionTitle = "UPCOMING TASKS",
                    tasks = emptyList(),
                    onTaskCardCLick = {},
                    onCheckBoxClick = {}
                )
                item { 
                    Spacer(modifier = Modifier.height(20.dp))
                }
                studySessionsList(
                    sectionTitle = "RECENT STUDY SESION",
                    sessions = sessions,
                    onDeleteClick = {}
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Scholar Navigate",
                style = MaterialTheme.typography.headlineMedium,
                color =if (isSystemInDarkTheme()) onPrimaryLight else onPrimaryDark
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) primaryLight else primaryDark
        )
    )
}

@Composable
private fun CountCardSection(
    modifier: Modifier = Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Course Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
    }
}

@Composable
private fun SubjectCardSection(
    modifier: Modifier = Modifier,
    courseList: List<Course>,
    emptyLazyList: String = "You don't have any courses. \n Click the + button to add new course",
    onAddCourseClick: () -> Unit
) {
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "COURSES",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddCourseClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Courses"
                )
            }
        }
        if (courseList.isEmpty()){
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.books),
                contentDescription = emptyLazyList
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyLazyList,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ){
            items(courseList){ Course ->
                SubjectCard(
                    courseName = Course.name,
                    gradientColors = Course.colors,
                    onClick = {

                    }
                )
            }
        }
    }
}