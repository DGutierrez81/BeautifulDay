package com.project.beautifulday.Network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Este módulo proporciona una instancia de FirebaseFirestore para la inyección de dependencias.

@Module
@InstallIn(SingletonComponent::class)
object FirebaseStore {

    // Proporciona una instancia única de FirebaseFirestore.
    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseFirestore = Firebase.firestore
}