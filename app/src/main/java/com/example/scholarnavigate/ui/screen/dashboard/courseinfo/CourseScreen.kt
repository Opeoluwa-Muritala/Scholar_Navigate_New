package com.example.scholarnavigate.ui.screen.dashboard.courseinfo

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.sessions
import com.example.scholarnavigate.tasks
import com.example.scholarnavigate.ui.components.CountCard
import com.example.scholarnavigate.ui.screen.dashboard.components.AddCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.screen.dashboard.components.todoTasksList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme


@Composable
fun CourseScreenNavigation(){
    CourseScreen()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseScreen() {
    val listState = rememberLazyListState()
    val isFABExpanded by remember { derivedStateOf{listState.firstVisibleItemIndex == 0}}
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var studyHours by remember {
        mutableStateOf("")
    }
    var courseName by remember {
        mutableStateOf("")
    }
    var selectedCardColor by remember {
        mutableStateOf(Course.subjectCardColors.random())
    }

    ScholarNavigateTheme(isSystemInDarkTheme()) {
        var isAddSubjectDialogOpen by remember { mutableStateOf(false) }
        AddCourseDialog(
            onDissmisRequest = { isAddSubjectDialogOpen = false },
            courseName = courseName,
            studyHours = studyHours,
            oncourseNameChange ={courseName = it},
            onStudyHoursChange = {studyHours = it},
            onConfirmButtonClick = { isAddSubjectDialogOpen = false },
            isOpen = isAddSubjectDialogOpen,
            selectedColors = selectedCardColor,
            onColorChange = {
                selectedCardColor = it
            }
        )


        var isDeleteCourseOpen by remember {
            mutableStateOf(false)
        }
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
        DeleteCourseDialog(
            title = "Delete Course",
            onDissmisRequest = { isDeleteCourseOpen = false },
            body = "Are you sure you want to delete this course? All related" +
                    "tasks and study sessions will be permanently removed. This action cannot be undone",
            onConfirmButtonClick = { isDeleteCourseOpen = false },
            isOpen = isDeleteCourseOpen
        )
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CourseinfoTopBar(
                    title = "CHM 121",
                    onBackButtonClick = { /*TODO*/ },
                    onDeleteButtonClick = { isDeleteCourseOpen = true },
                    onEditButtonClick = {/*TODO*/},
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { /*TODO*/ },
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
                        studiedHours = "10",
                        goalHours = "15",
                        progress = 0.75f
                    )
                }
                item {
                    Text(
                        text = "Course Info",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = " CHM 121 is a course bla that bla exist in bla bla bla ejo. It consist of many intricasies",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )

                }
                todoTasksList(
                    sectionTitle = "UPCOMING TASKS",
                    tasks = tasks,
                    onTaskCardCLick = {},
                    onCheckBoxClick = {}
                )
                todoTasksList(
                    sectionTitle = "COMPLETED TASKS",
                    tasks = tasks,
                    onTaskCardCLick = {},
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