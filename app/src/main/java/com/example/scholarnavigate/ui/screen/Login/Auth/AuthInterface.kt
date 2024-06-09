package com.example.scholarnavigate.ui.screen.Login.Auth

import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthInterface {

    fun googleSignInCredential(credential: AuthCredential): Flow<AuthCredential>
}