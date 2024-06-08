package com.project.beautifulday.Network

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Este módulo proporciona una instancia de FirebaseAuth para la inyección de dependencias.

@Module
@InstallIn(SingletonComponent::class)
object FirebaseAuth {

    // Proporciona una instancia única de FirebaseAuth.
    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

}