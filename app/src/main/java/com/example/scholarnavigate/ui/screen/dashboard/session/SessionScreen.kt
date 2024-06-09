package com.example.scholarnavigate.ui.screen.dashboard.session

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_CANCEL
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_START
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_STOP
import com.example.scholarnavigate.ui.screen.dashboard.components.DeleteCourseDialog
import com.example.scholarnavigate.ui.screen.dashboard.components.SelectCourseField
import com.example.scholarnavigate.ui.screen.dashboard.components.studySessionsList
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.viewmodels.SessionViewModel
import kotlin.time.Duration

@Composable
fun SessionScreenNavigation(navController: NavController, timerService: SessionTimerService){
    val viewModel: SessionViewModel = hiltViewModel()


    SessionScreen(
        onBackButtonClick = {navController.navigateUp()},
        timerService
    )
}
@Composable
private fun SessionScreen(
   onBackButtonClick: () -> Unit,
   timerService: SessionTimerService
) {
    val context = LocalContext.current
    var isCourseFieldOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var courseFieldText by rememberSaveable {
        mutableStateOf<Course?>(null)
    }
    val hours by timerService.hours
    val minutes by timerService.minutes
    val seconds by timerService.second
    val currentTimerState by timerService.currentState


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
                            .aspectRatio(1f),
                        hours = hours,
                        seconds = seconds,
                        minutes = minutes
                    )
                }
                item {
                    SelectCourseField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        isOpen = isCourseFieldOpen,
                        courses = emptyList(),
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
                        seconds = seconds,
                        timerState = currentTimerState,
                        startButtonClick = {
                            ServiceHelper.triggerForeGroundService(
                                context,
                                if (currentTimerState == TimerState.STARTED) ACTION_SERVICE_STOP else ACTION_SERVICE_START
                            )
                                           },
                        cancelButtonClick = {
                            ServiceHelper.triggerForeGroundService(context, ACTION_SERVICE_CANCEL)
                        },
                        finishButtonClick = {}
                    )
                }
                studySessionsList(
                    sectionTitle = "SIMILAR SESSIONS HISTORY",
                    sessions = emptyList(),
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
    modifier: Modifier,
    hours: String,
    minutes: String,
    seconds: String
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
        Row{
            AnimatedContent(
                targetState = hours,
                label = hours,
                transitionSpec = { timerTextAnimation() }
            ) { hours ->
                Text(
                    text = hours,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 45.sp)
                )
            }
            AnimatedContent(
                targetState = minutes,
                label = minutes,
                transitionSpec = { timerTextAnimation() }
            ) { minutes ->
                Text(
                    text = minutes,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 45.sp)
                )
            }
            AnimatedContent(
                targetState = seconds,
                label = seconds,
                transitionSpec = { timerTextAnimation() }
            ) { seconds ->
                Text(
                    text = seconds,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 45.sp)
                )
            }

        }

    }
}


@Composable
private fun ButtonSection(
    modifier: Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit,
    timerState: TimerState,
    seconds: String
){
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = cancelButtonClick,
            enabled = timerState != TimerState.STARTED && seconds != "00"
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = "Cancel"
            )
        }
        Button(
            onClick = startButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (timerState == TimerState.STARTED) Color.Red else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = when (timerState){
                    TimerState.IDLE -> "Start"
                    TimerState.STARTED -> "Stop"
                    TimerState.STOPPED -> "Resume"
                }
            )
        }
        Button(
            onClick = finishButtonClick,
            enabled = timerState != TimerState.STARTED && seconds != "00"
            ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = "Finish"
            )
        }
    }
}

private fun timerTextAnimation(duration: Int = 600): ContentTransform {
    return slideInVertically(animationSpec = tween(duration)) { it} +
            fadeIn(animationSpec = tween(600)) togetherWith
            slideOutVertically(animationSpec = tween(duration)) { -it} +
            fadeOut(animationSpec = tween(600))
}