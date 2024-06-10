package com.example.firebaseauthyt.di

import com.example.firebaseauthyt.data.AuthRepository
import com.example.scholarnavigate.data.database.local.data_access_objects.UserDao
import com.example.scholarnavigate.data.database.local.repository.AuthRepositoryImpl
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth, userRepository: UserRepository):AuthRepository{
        return AuthRepositoryImpl(firebaseAuth, userRepository)
    }
}