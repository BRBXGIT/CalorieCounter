package com.example.caloriecounter.auth.presentation

import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

@HiltViewModel
class AuthScreenVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth
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

    fun getUser() {
        Log.d("XXXX", firebaseAuth.currentUser?.email.toString())
    }
}