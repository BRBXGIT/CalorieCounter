package com.example.caloriecounter.navigation_drawer_screens.screens.profile_screen.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caloriecounter.R
import com.example.caloriecounter.custom_toasts.ErrorMessage
import com.example.caloriecounter.custom_toasts.SuccessMessage
import com.example.caloriecounter.navigation.AuthScreen
import com.example.caloriecounter.navigation_drawer_screens.screens.profile_screen.data.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    firebaseAuth: FirebaseAuth,
    profileScreenVM: ProfileScreenVM,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    var isError by rememberSaveable { mutableStateOf(false) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_navigation_arrow_left),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    var logOutDialogOpen by rememberSaveable { mutableStateOf(false) }
                    if(logOutDialogOpen) {
                        LogOutDialog(
                            onDismissRequest = { logOutDialogOpen = false },
                            onConfirmation = {
                                logOutDialogOpen = false
                                navController.navigate(AuthScreen) {
                                    popUpTo(0)
                                }
                                profileScreenVM.logOut()
                            }
                        )
                    }
                    IconButton(
                        onClick = {
                            logOutDialogOpen = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_log_out),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            val user = User(
                name = firebaseAuth.currentUser?.displayName,
                profilePictureUrl = firebaseAuth.currentUser?.photoUrl
            )
            Spacer(modifier = Modifier.height(0.dp))

            Box(
                modifier = Modifier
                    .size(164.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        if(uri != null) {
                            scope.launch {
                                if(profileScreenVM.updateUserPicture(uri)) {
                                    isSuccess = true
                                } else {
                                    isError = true
                                }
                            }
                        }
                    }
                )

                if(user.profilePictureUrl != null) {
                    AsyncImage(
                        model = user.profilePictureUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null
                        )
                    }
                }
            }

            var userName by rememberSaveable { mutableStateOf(
                if((user.name != null) && (user.name != "")) {
                    user.name
                } else {
                    "User"
                }
            ) }

            val focusManager = LocalFocusManager.current
            TextField(
                value = userName,
                onValueChange = { userName = it },
                maxLines = 1,
                modifier = Modifier.width(220.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    scope.launch {
                        focusManager.clearFocus()
                        if(profileScreenVM.updateUserName(userName)) {
                            isSuccess = true
                        } else {
                            isError = true
                        }
                    }
                })
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(visible = isError) {
                ErrorMessage {
                    isError = false
                }
            }
            AnimatedVisibility(visible = isSuccess) {
                SuccessMessage {
                    isSuccess = false
                }
            }
        }
    }
}