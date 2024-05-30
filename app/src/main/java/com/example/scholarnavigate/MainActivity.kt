package com.example.scholarnavigate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.navigate.RootNavigationGraph
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScholarNavigateTheme(darkTheme = isSystemInDarkTheme()) {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}


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




