package com.example.caloriecounter.auth.presentation

import androidx.lifecycle.ViewModel
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appRepositoryImpl: AppRepositoryImpl
): ViewModel() {

    //Function creating user with email
    suspend fun createUser(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    //Function for sign in with email(not google)
    suspend fun signIn(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    //Check have been user used this app before
    fun getUserCalorieData(): Flow<UserCalorieData?> {
        return appRepositoryImpl.getUserCalorieData()
    }

    fun sendMailForRecoverPassword(email: String): Boolean {
        try {
            firebaseAuth.sendPasswordResetEmail(email)
            return true
        } catch(e: Exception) {
            return false
        }
    }
}