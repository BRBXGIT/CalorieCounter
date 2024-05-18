package com.example.caloriecounter.auth.di

import android.content.Context
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(@ApplicationContext context: Context, firebaseAuth: FirebaseAuth): GoogleAuthUiClient {
        return GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
            firebaseAuth = firebaseAuth
        )
    }
}