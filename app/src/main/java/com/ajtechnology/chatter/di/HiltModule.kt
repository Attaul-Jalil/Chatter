package com.ajtechnology.chatter.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthentication():FirebaseAuth = Firebase.auth

    @Provides
    fun provideFireStoreDB():FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideStorage():FirebaseStorage = FirebaseStorage.getInstance()

}