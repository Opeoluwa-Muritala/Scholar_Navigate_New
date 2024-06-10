package com.example.scholarnavigate.data.database.local.repository

import com.example.firebaseauthyt.data.AuthRepository
import com.example.firebaseauthyt.util.Resource
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.example.scholarnavigate.data.module.UserData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : AuthRepository {

    override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val userData =  UserData(
                    userId = firebaseUser.uid.toInt(),
                    username =  firebaseUser.displayName ?: "",
                    profilePictureUrl = firebaseUser.photoUrl.toString(),
                    email = firebaseUser.email ?: ""
                )
                userRepository.saveUserData(userData)
            } else {
                emit(Resource.Error("User Data Not Available"))
            }
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }


}