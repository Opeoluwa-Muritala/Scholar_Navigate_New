package com.example.scholarnavigate.ui.screen.dashboard.courseinfo

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scholarnavigate.data.uimodule.CourseState
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.ui.components.CountCard
import com.example.scholarnavigate.ui.screen.dashboard.components.AddCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.viewmodels.CourseViewModel
import com.example.scholarnavigate.viewmodels.events.CourseEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CourseScreenNavigation(navController: NavController){
    val viewModel: CourseViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val navBackstackEntry by navController.currentBackStackEntryAsState()
    val courseId = navBackstackEntry?.arguments?.getInt("courseId", 1)

    LaunchedEffect(key1 = viewModel.snackBarEvenFlow){
        viewModel.snackBarEvenFlow.collectLatest {
            when (it){
                is SnackBarEvent.NavigateUp -> navController.navigateUp()
                else -> {}
            }

        }
    }

    CourseScreen(
        state = state,
        onEvent = viewModel::onEvent,
        snackBarEvent = viewModel.snackBarEvenFlow,
        onAddTaskButton = {navController.navigate(DashboardScreens.TaskScreen.createTask(0, courseId ?:1))},
        onBackButtonClick = {navController.navigateUp()},
        onTaskCardClick = {navController.navigate(DashboardScreens.TaskScreen.createTask(courseId = courseId ?: 1, taskId = it ?: 1))}
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseScreen(
    state: CourseState,
    onEvent: (CourseEvent) -> Unit,
    onAddTaskButton: () -> Unit,
    onBackButtonClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    snackBarEvent: SharedFlow<SnackBarEvent>,
) {
    val listState = rememberLazyListState()
    val isFABExpanded by remember { derivedStateOf{listState.firstVisibleItemIndex == 0}}
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    ScholarNavigateTheme(isSystemInDarkTheme()) {
        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(key1 = true){
            snackBarEvent.collectLatest { event ->
                when (event) {
                    is SnackBarEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = event.duration
                        )
                    }

                    else -> {}
                }
            }
        }

        LaunchedEffect(key1 = state.studiedHours, key2 = state.goalStudyHours){
            onEvent(CourseEvent.UpdateProgress)
        }


        var isAddSubjectDialogOpen by remember { mutableStateOf(false) }
        AddCourseDialog(
            onDissmisRequest = { isAddSubjectDialogOpen = false },
            courseName = state.courseName,
            studyHours = state.goalStudyHours,
            oncourseNameChange ={onEvent(CourseEvent.OnCourseCardNameChange(it))},
            onStudyHoursChange = {onEvent(CourseEvent.OnGoalStudyHoursChange(it))},
            onConfirmButtonClick = {
                onEvent(CourseEvent.UpdateSubject)
                isAddSubjectDialogOpen = false
                                   },
            isOpen = isAddSubjectDialogOpen,
            selectedColors = state.courseCardColors,
            onColorChange = {
                onEvent(CourseEvent.OnCourseCardColorChange(it))
            }
        )


        var isDeleteCourseOpen by remember {
            mutableStateOf(false)
        }
        var isDeleteDialogOpen by remember {
            mutableStateOf(false)
        }
        DeleteCourseDialog(
            onDissmisRequest = {isDeleteDialogOpen = false},
            body = "Are you sure you want to delete this session? Your study hours will be reduced " +
                    "by this time. This action cannot be undone",
            onConfirmButtonClick = {
                onEvent(CourseEvent.DeleteSession)
                isDeleteDialogOpen = false },
            isOpen = isDeleteDialogOpen
        )
        DeleteCourseDialog(
            title = "Delete Course",
            onDissmisRequest = { isDeleteCourseOpen = false },
            body = "Are you sure you want to delete this course? All related" +
                    "tasks and study sessions will be permanently removed. This action cannot be undone",
            onConfirmButtonClick = {
                onEvent(CourseEvent.DeleteCourse)
                isDeleteCourseOpen = false

                                   },
            isOpen = isDeleteCourseOpen
        )
        Scaffold(
            snackbarHost = { SnackbarHost (snackbarHostState) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CourseinfoTopBar(
                    title = state.courseName,
                    onBackButtonClick = onBackButtonClick,
                    onDeleteButtonClick = { isDeleteCourseOpen = true },
                    onEditButtonClick = {isAddSubjectDialogOpen = true},
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = onAddTaskButton,
                    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")},
                    text = { Text(text = "Add Task")},
                    expanded = isFABExpanded
                ) 
            }
        ) { paddingValues ->
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = listState
            )
            {
                item {
                    CourseOverviewSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        studiedHours = state.studiedHours.toString(),
                        goalHours = state.goalStudyHours,
                        progress = state.progress
                    )
                }
                todoTasksList(
                    sectionTitle = "UPCOMING TASKS",
                    tasks = state.upcomingTask,
                    onTaskCardCLick = { onTaskCardClick(it.taskId) },
                    onCheckBoxClick = {onEvent(CourseEvent.OnTaskIsCompleteChange(it)) }
                )
                todoTasksList(
                    sectionTitle = "COMPLETED TASKS",
                    tasks = state.completedTask,
                    onTaskCardCLick = { onTaskCardClick(it.taskId) },
                    onCheckBoxClick = {onEvent(CourseEvent.OnTaskIsCompleteChange(it))}
                )
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                studySessionsList(
                    sectionTitle = "RECENT STUDY SESSION",
                    sessions = state.recentSession,
                    onDeleteClick = {
                        onEvent(CourseEvent.OnDeleteSessionButtonClick(it))
                        isDeleteDialogOpen = true
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseinfoTopBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
             IconButton(onClick = onBackButtonClick) {
                 Icon(
                     imageVector = Icons.Default.ArrowBack,
                     contentDescription = "Navigate Back"
                 )
             }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Course"
                )
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Course"
                )
            }
        }
    )
}

@Composable
private fun CourseOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress:Float
) {
    val percentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0,100)
    }
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProgress")
        }
    }
}