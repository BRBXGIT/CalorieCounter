package com.example.caloriecounter.navigation_drawer_screens.profile_screen.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.caloriecounter.app.data.repository.AppRepositoryImpl
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProfileScreenVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appRepositoryImpl: AppRepositoryImpl
): ViewModel() {

    fun logOut() {
        firebaseAuth.signOut()
    }

    suspend fun updateUserName(name: String): Boolean {
        val result = CompletableDeferred<Boolean>()
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.addOnSuccessListener {
            result.complete(true)
        }

        return result.await()
    }

    suspend fun updateUserPicture(image: Uri): Boolean {
        val storageRef = FirebaseStorage.getInstance().reference.child("Users/${firebaseAuth.currentUser?.uid}/${image.lastPathSegment}")
        val upload = storageRef.putFile(image)

        val result = CompletableDeferred<Boolean>()
        upload.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }
                firebaseAuth.currentUser?.updateProfile(profileUpdates)?.addOnSuccessListener {
                    result.complete(true)
                }
            }
        }.addOnFailureListener {
            result.complete(false)
        }

        return result.await()
    }

    fun getUserData(): Flow<UserCalorieData> {
        return appRepositoryImpl.getUserCalorieData()
    }
}