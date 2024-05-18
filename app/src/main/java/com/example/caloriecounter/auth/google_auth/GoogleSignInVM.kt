package com.example.caloriecounter.auth.google_auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoogleSignInVM: ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: FirebaseUser?) {
        _state.update { it.copy(
            result != null,
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}