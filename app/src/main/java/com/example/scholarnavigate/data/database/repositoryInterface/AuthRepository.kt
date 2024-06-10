package com.example.firebaseauthyt.data

import com.example.firebaseauthyt.util.Resource
import com.example.scholarnavigate.data.module.UserData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
}