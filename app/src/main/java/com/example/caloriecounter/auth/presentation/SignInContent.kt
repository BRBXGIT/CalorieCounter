package com.example.caloriecounter.auth.presentation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import com.example.caloriecounter.auth.google_auth.GoogleSignInVM
import com.example.caloriecounter.custom_toasts.ErrorMessage
import com.example.caloriecounter.navigation.AuthScreen
import com.example.caloriecounter.navigation.AuthScreensGraph
import com.example.caloriecounter.navigation.MainScreensGraph
import com.example.caloriecounter.navigation.RecoverPasswordScreen
import com.example.caloriecounter.navigation.StartScreen
import com.example.caloriecounter.ui.theme.dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInContent(
    authVM: AuthVM,
    googleAuthUiClient: GoogleAuthUiClient,
    navController: NavHostController,
    googleSignInVM: GoogleSignInVM = viewModel<GoogleSignInVM>(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val userCalorieData = authVM.getUserCalorieData().collectAsState(initial = null).value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.authScreensSpacer),
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        var emailError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }
        var authenticationError by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = emailError, key2 = passwordError) {
            delay(3000)
            emailError = false
            passwordError = false
        }

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorContainerColor = Color.Transparent,
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = null
                )
            },
            isError = emailError
        )

        var passwordVisible by remember { mutableStateOf(false) }
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                errorContainerColor = Color.Transparent,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_passwordd),
                    contentDescription = null
                )
            },
            isError = passwordError,
            trailingIcon = {
                val icon = if(passwordVisible) {
                    R.drawable.ic_eye_open
                } else {
                    R.drawable.ic_eye_closed
                }

                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(0.dp))

        Button(
            onClick = {
                scope.launch(Dispatchers.Main) {
                    if(email.isBlank()) {
                        emailError = true
                    }
                    if(password.isBlank()) {
                        passwordError = true
                    }
                    if((!passwordError) && (!emailError)) {
                        if(!authVM.signIn(email, password)) {
                            authenticationError = true
                        } else {
                            if(userCalorieData == null) {
                                navController.navigate(StartScreen) {
                                    popUpTo(AuthScreen) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navController.navigate(MainScreensGraph) {
                                    popUpTo(AuthScreensGraph) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = "Sign in"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(0.45f)
            )

            Text(
                text = "OR"
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(0.45f)
            )
        }

        //State and launcher for google sign in
        val state by googleSignInVM.state.collectAsStateWithLifecycle()

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if(result.resultCode == RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        googleSignInVM.onSignInResult(signInResult)
                    }
                }
            }
        )

        //Check succesfull of signing in with google
        LaunchedEffect(key1 = state.isSignInSuccessful, key2 = state.signInErrorMessage != null) {
            if(state.isSignInSuccessful) {
                googleSignInVM.resetState()
                if(userCalorieData == null) {
                    navController.navigate(StartScreen) {
                        popUpTo(AuthScreen) {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(MainScreensGraph) {
                        popUpTo(AuthScreensGraph) {
                            inclusive = true
                        }
                    }
                }
            }
            if(state.signInErrorMessage != null) {
                authenticationError = true
            }
        }

        OutlinedButton(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val signInIntentSender = googleAuthUiClient.signInWithGoogle()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.Transparent)
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = RoundedCornerShape(100.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Sign in with google"
                )
            }
        }
        
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.signInScreenBottomSpacer))

        TextButton(
            onClick = { navController.navigate(RecoverPasswordScreen) }
        ) {
            Text(
                text = "Lost password?",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(visible = authenticationError) {
            ErrorMessage(
                onTimeEnds = { authenticationError = false },
                text = "Something went wrong"
            )
        }
    }
}