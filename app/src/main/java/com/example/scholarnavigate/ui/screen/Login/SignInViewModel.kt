package com.example.firebaseauthyt.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.example.scholarnavigate.data.module.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun addUser(userData: UserData){
        viewModelScope.launch {
            userRepository.saveUserData(userData)
        }
    }

    fun isUserLoggedIn(): Boolean {
        var launch by mutableStateOf(false)
        viewModelScope.launch {
            userRepository.getUserCount().collect {
                launch = it > 0
            }
        }
        return launch
    }
}