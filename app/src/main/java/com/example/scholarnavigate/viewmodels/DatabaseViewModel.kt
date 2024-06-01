package com.example.scholarnavigate.viewmodels

import androidx.lifecycle.ViewModel
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val courseRepository: CourseRepository
): ViewModel() {

}