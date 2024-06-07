package com.example.scholarnavigate.ui.screen.dashboard.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scholarnavigate.courses
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_CANCEL
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_START
import com.example.scholarnavigate.sessions
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SelectCourseField
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.viewmodels.SessionViewModel

@Composable
fun SessionScreenNavigation(navController: NavController){
    val viewModel: SessionViewModel = hiltViewModel()


    SessionScreen(
        onBackButtonClick = {navController.navigateUp()}
    )
}
@Composable
private fun SessionScreen(
   onBackButtonClick: () -> Unit
) {
    val context = LocalContext.current
    var isCourseFieldOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var courseFieldText by rememberSaveable {
        mutableStateOf<Course?>(null)
    }


    ScholarNavigateTheme {
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
                SessionScreenTopAppBar(
                    onBackCLick = onBackButtonClick
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    TimerSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }
                item {
                    SelectCourseField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        isOpen = isCourseFieldOpen,
                        courses = courses,
                        onCourseClicked = { course ->
                            courseFieldText = course
                            isCourseFieldOpen = false
                        },
                        onExpandedChange = { isCourseFieldOpen = !isCourseFieldOpen },
                        value = courseFieldText?.name.orEmpty(),
                        onDismissRequest = { isCourseFieldOpen = false }
                    )
                }
                item {
                    ButtonSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        startButtonClick = {
                            ServiceHelper.triggerForeGroundService(context, ACTION_SERVICE_START)
                                           },
                        cancelButtonClick = {
                            ServiceHelper.triggerForeGroundService(context, ACTION_SERVICE_CANCEL)
                        },
                        finishButtonClick = {}
                    )
                }
                studySessionsList(
                    sectionTitle = "SIMILAR SESSIONS HISTORY",
                    sessions = sessions,
                    onDeleteClick = { isDeleteDialogOpen = true }
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
        Box(modifier = modifier
            .padding(12.dp)
            .size(25.dp)
            .border(8.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )
        Text(
            text = "00:09:29",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 45.sp)
        )

    }
}


@Composable
private fun ButtonSection(
    modifier: Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit
){
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = cancelButtonClick) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = "Cancel"
            )
        }
        Button(onClick = startButtonClick) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = "Start"
            )
        }
        Button(onClick = finishButtonClick) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = "Finish"
            )
        }
    }
}

