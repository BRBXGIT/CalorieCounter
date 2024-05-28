@file:Suppress("DEPRECATION")

package com.example.caloriecounter.auth.google_auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.caloriecounter.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

@Suppress("DEPRECATION")
class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val firebaseAuth: FirebaseAuth
) {
    //Sign in with google function
    suspend fun signInWithGoogle(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    //Sign in with intent and credentials, return a signInState DataClass
    //Which contains user and error's if they were
    suspend fun signInWithIntent(intent: Intent): SignInState {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            SignInState(
                isSignInSuccessful = firebaseAuth.signInWithCredential(googleCredentials).await().user != null,
                signInErrorMessage = null
            )
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) {
                throw e
            } else {
                SignInState(
                    isSignInSuccessful = false,
                    signInErrorMessage = e.message
                )
            }
        }
    }

    //Building sign in request, easy to understand
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}