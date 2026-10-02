package com.example.firebaseauthyt.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.example.scholarnavigate.data.module.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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

    suspend fun isUserLoggedIn(): Boolean {
        return userRepository.getUserCount().first() > 0
    }
}