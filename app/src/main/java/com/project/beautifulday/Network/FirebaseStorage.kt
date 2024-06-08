package com.project.beautifulday.Network

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Este módulo proporciona una instancia de FirebaseStorage para la inyección de dependencias.
@Module
@InstallIn(SingletonComponent::class)
object FirebaseStorage {

    // Proporciona una instancia única de FirebaseStorage.
    @Singleton
    @Provides
    fun ProvidesStorge(): FirebaseStorage = Firebase.storage
}