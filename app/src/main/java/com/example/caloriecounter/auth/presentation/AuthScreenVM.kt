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
class AuthScreenVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appRepositoryImpl: AppRepositoryImpl
): ViewModel() {

    suspend fun createUser(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    suspend fun signIn(email: String, password: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            result.complete(it.isSuccessful)
        }
        return result.await()
    }

    fun getUserCalorieData(): Flow<UserCalorieData?> {
        return appRepositoryImpl.getUserCalorieData()
    }
}