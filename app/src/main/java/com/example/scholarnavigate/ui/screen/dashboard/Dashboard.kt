package com.example.scholarnavigate.ui.screen.dashboard

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.firebaseauthyt.presentation.login_screen.SignInViewModel
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.data.module.UserData
import com.example.scholarnavigate.data.uimodule.DashboardState
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.example.scholarnavigate.data.util.webclientId
import com.example.scholarnavigate.navigate.Graph
import com.example.scholarnavigate.navigate.navmodule.AuthScreen
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.ui.components.CountCard
import com.example.scholarnavigate.ui.screen.dashboard.components.AddCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SubjectCard
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.viewmodels.DashboardViewModel
import com.example.scholarnavigate.viewmodels.events.DashBoardEvent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DashboardScreenNavigation(navController: NavController){
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val recentSessions by viewModel.sessions.collectAsState()


    DashboardScreen(
        state = state,
        onEvent = viewModel::onEvent,
        tasks = tasks,
        recentSession = recentSessions,
        snackBarEvent = viewModel.snackBarEvenFlow,
        onCourseCardClick = {
            navController.navigate(DashboardScreens.CourseScreen.createCourse(it ?: 0))
                            },
        onStartStudySessionButtonClick = {
            navController.navigate(DashboardScreens.SessionScreen.route)
                                         },
        onTaskCardClick = {
            navController.navigate(DashboardScreens.TaskScreen.createTask(courseId = 0, taskId = it ?: 1))
        },
        navigate = {
            viewModel.logout(navController)
            navController.navigate(Graph.AUTHENTCATION){
                popUpTo(0)
            }
        },
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    tasks: List<Task>,
    recentSession: List<Session>,
    snackBarEvent: SharedFlow<SnackBarEvent>,
    onEvent: (DashBoardEvent) -> Unit,
    onCourseCardClick:(Int?) -> Unit,
    onTaskCardClick:(Int?) -> Unit,
    onStartStudySessionButtonClick:() -> Unit,
    navigate: () -> Unit,
) {


    ScholarNavigateTheme(isSystemInDarkTheme()) {
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(key1 = true) {
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


        var isAddSubjectDialogOpen by remember { mutableStateOf(false) }
        AddCourseDialog(
            onDissmisRequest = { isAddSubjectDialogOpen = false },
            courseName = state.courseName,
            studyHours = state.goalStudyHours,
            oncourseNameChange = { onEvent(DashBoardEvent.onCourseNameChange(it)) },
            onStudyHoursChange = { onEvent(DashBoardEvent.onGoalStudyHoursChange(it)) },
            onConfirmButtonClick = {
                onEvent(DashBoardEvent.SaveCourse)
                isAddSubjectDialogOpen = false
            },
            isOpen = isAddSubjectDialogOpen,
            selectedColors = state.courseCardColors,
            onColorChange = {
                onEvent(DashBoardEvent.onCourseCardColorChange(it))
            }
        )


        var isDeleteDialogOpen by remember {
            mutableStateOf(false)
        }
        DeleteCourseDialog(
            onDissmisRequest = { isDeleteDialogOpen = false },
            body = "Are you sure you want to delete this session? Your study hours will be reduced " +
                    "by this time. This action cannot be undone",
            onConfirmButtonClick = {
                isDeleteDialogOpen = false
                onEvent(DashBoardEvent.DeleteSession)
            },
            isOpen = isDeleteDialogOpen
        )
        
        val scope = rememberCoroutineScope()
        
        val sheetState = rememberModalBottomSheetState()

        var bottomSheet by remember {
            mutableStateOf(false)
        }
        if(bottomSheet){
            ModalBottomSheet(
                content = {
                    DrawerContent(
                        onLogOutClick = {
                            navigate()
                        }
                    )

                },
                sheetState = sheetState,
                onDismissRequest = {
                    bottomSheet = false
                    scope.launch {
                        sheetState.hide()
                    }
                }
            )
        }
        

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                DashboardTopAppBar(
                    onIconClick = {
                        bottomSheet = true
                        scope.launch{
                                sheetState.show()
                        }

                    }
                )

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
                        subjectCount = state.totalCourseCount,
                        studiedHours = state.totalStudiedHours.toString(),
                        goalHours = state.totalGoalStudyHours.toString()
                    )
                }
                item {
                    SubjectCardSection(
                        modifier = Modifier.fillMaxWidth(),
                        courseList = state.courses,
                        onAddCourseClick = {
                            isAddSubjectDialogOpen = true
                        },
                        onCourseCardClick = { courseId ->
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
                    onTaskCardCLick = { onTaskCardClick(it.taskId) },
                    onCheckBoxClick = {
                        onEvent(DashBoardEvent.onTaskIsCompleteChange(it))
                    }
                )
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                studySessionsList(
                    sectionTitle = "RECENT STUDY SESSION",
                    sessions = recentSession,
                    onDeleteClick = {
                        isDeleteDialogOpen = true
                        onEvent(DashBoardEvent.onDeleteSessionButtonClick(it))
                    }
                )
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopAppBar(
    onIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Scholar Navigate",
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            Row(
                Modifier.clickable {
                    onIconClick()
                }
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.profile_icon),
                        contentDescription = "No Image Found",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

            }

        }
    )
}

@Composable
private fun DrawerContent(
    onLogOutClick:() -> Unit
){
    Column(
        Modifier.fillMaxSize(),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = "No Image Found",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                Modifier.clickable {
                    onLogOutClick()
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.sign_out),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Log Out"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Log Out", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 40.sp))
            }
        }
    }
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
            items(courseList) { course ->
                SubjectCard(
                    courseName = course.name,
                    gradientColors = course.colors.map { Color(it) },
                    onClick = { onCourseCardClick(course.courseId) }
                )
            }
        }
    }
}