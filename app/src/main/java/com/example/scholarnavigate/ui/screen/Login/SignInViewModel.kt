package com.example.firebaseauthyt.presentation.login_screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthyt.data.AuthRepository
import com.example.firebaseauthyt.util.Resource
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.data.module.UserData
import com.example.scholarnavigate.data.uimodule.TaskState
import com.example.scholarnavigate.data.util.Priority
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    private val _snackbarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()

    val _state = MutableStateFlow<UserData?>(null)
    val state = _state.asStateFlow()

    fun fetchUser() {
        viewModelScope.launch {
            try {
                userRepository
                    .getsavedUser(1)?.let { user ->
                        _state.update {
                            it?.copy(
                                userId = user.userId,
                                username = user.username,
                                profilePictureUrl = user.profilePictureUrl
                            )
                        }
                    }

            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Find User ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    fun addUser(user: UserData){
        viewModelScope.launch {
            try {
                userRepository
                    .saveUserDate(user).let {
                        _state.update {
                            it?.copy(
                                userId = user.userId,
                                username = user.username,
                                profilePictureUrl = user.profilePictureUrl,
                                email = user.email
                            )
                        }
                    }

            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Add New User ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
    fun removeUser(){
        viewModelScope.launch {
            try {
                userRepository
                    .deleteUserData(1)

            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Delete User ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        repository.googleSignIn(credential).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _googleState.value = GoogleSignInState(success = result.data)
                }
                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }
            }
        }
    }
}