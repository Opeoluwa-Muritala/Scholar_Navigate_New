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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.scholarnavigate.R
import com.example.scholarnavigate.courses
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.uimodule.DashboardState
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.sessions
import com.example.scholarnavigate.tasks
import com.example.scholarnavigate.ui.components.CountCard
import com.example.scholarnavigate.ui.screen.dashboard.components.AddCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SubjectCard
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.viewmodels.DashboardViewModel
import com.example.scholarnavigate.viewmodels.events.DashBoardEvent

@Composable
fun DashboardScreenNavigation(navController: NavController){
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardScreen(
        state = state,
        onCourseCardClick = {
            navController.navigate("${DashboardScreens.CourseScreen.route}/$it")
                            },
        onStartStudySessionButtonClick = {
            navController.navigate(DashboardScreens.SessionScreen.route)
                                         },
        onTaskCardClick = {
            navController.navigate(DashboardScreens.TaskScreen.route)
        }
    )
}
@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashBoardEvent) -> Unit
    onCourseCardClick:(Int?) -> Unit,
    onTaskCardClick:(Int?) -> Unit,
    onStartStudySessionButtonClick:() -> Unit,
) {




    ScholarNavigateTheme(isSystemInDarkTheme()) {
        var isAddSubjectDialogOpen by remember { mutableStateOf(false) }
        AddCourseDialog(
            onDissmisRequest = { isAddSubjectDialogOpen = false },
            courseName = state.courseName,
            studyHours = state.goalStudyHours,
            oncourseNameChange ={},
            onStudyHoursChange = {},
            onConfirmButtonClick = { isAddSubjectDialogOpen = false },
            isOpen = isAddSubjectDialogOpen,
            selectedColors = state.courseCardColors,
            onColorChange = {

            }
        )


        var isDeleteDialogOpen by remember {
            mutableStateOf(false)
        }
        DeleteCourseDialog(
            onDissmisRequest = { isDeleteDialogOpen = false },
            body = "Are you sure you want to delete this session? Your study hours will be reduced " +
                    "by this time. This action cannot be undone",
            onConfirmButtonClick = { isDeleteDialogOpen = false },
            isOpen = isDeleteDialogOpen
        )
        Scaffold(
            topBar = {
                DashboardTopAppBar()
            },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    CountCardSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        subjectCount = 5,
                        studiedHours = state.totalStudiedHours.toString(),
                        goalHours = state.goalStudyHours.toString()
                    )
                }
                item {
                    SubjectCardSection(
                        modifier = Modifier.fillMaxWidth(),
                        courseList = state.courses,
                        onAddCourseClick = {
                            isAddSubjectDialogOpen = true
                        },
                        onCourseCardClick = {courseId ->
                            onCourseCardClick(courseId)
                        }
                    )
                }
                item {
                    Button(
                        onClick = onStartStudySessionButtonClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 20.dp)
                    ) {
                        Text(text = "Start Study Session")
                    }
                }
                todoTasksList(
                    sectionTitle = "UPCOMING TASKS",
                    tasks = tasks,
                    onTaskCardCLick = {onTaskCardClick(it.taskId)},
                    onCheckBoxClick = {}
                )
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                studySessionsList(
                    sectionTitle = "RECENT STUDY SESSION",
                    sessions = sessions,
                    onDeleteClick = {isDeleteDialogOpen = true}
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
            )
        },
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
    onAddCourseClick: () -> Unit,
    onCourseCardClick: (Int?) -> Unit
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
        if (courseList.isEmpty()) {
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
        ) {
            items(courseList) { Course ->
                SubjectCard(
                    courseName = Course.name,
                    gradientColors = Course.colors.map { Color(it) },
                    onClick = { onCourseCardClick(Course.courseId) }
                )
            }
        }
    }
}