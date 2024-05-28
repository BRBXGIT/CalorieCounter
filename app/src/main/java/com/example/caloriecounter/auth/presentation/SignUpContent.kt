package com.example.caloriecounter.auth.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.navigation.HomeScreen
import com.example.caloriecounter.navigation.StartScreen
import com.example.caloriecounter.ui.theme.dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//Simple sign up screen, nothing to comment)
@Composable
fun SignUpContent(
    authScreenVM: AuthScreenVM,
    navController: NavHostController,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.authScreensSpacer),
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var confirmPassword by rememberSaveable { mutableStateOf("") }

        var emailError by rememberSaveable { mutableStateOf(false) }
        var passwordError by rememberSaveable { mutableStateOf(false) }
        var confirmPasswordError by rememberSaveable { mutableStateOf(false) }

        var authenticationError by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(key1 = emailError, key2 = passwordError, key3 = confirmPasswordError) {
            delay(3000)
            emailError = false
            passwordError = false
            confirmPasswordError = false
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
            isError = emailError,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = null
                )
            },
        )

        var passwordVisible by rememberSaveable { mutableStateOf(false) }
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
            isError = passwordError,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_passwordd),
                    contentDescription = null
                )
            },
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

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirm password") },
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
            isError = confirmPasswordError,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_passwordd),
                    contentDescription = null
                )
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(0.dp))

        val userCalorieData = authScreenVM.getUserCalorieData().collectAsState(initial = null).value
        Button(
            onClick = {
                scope.launch(Dispatchers.Main) {
                    if(email.isBlank()) {
                        emailError = true
                    }
                    if(password.isBlank()) {
                        passwordError = true
                    }
                    if(confirmPassword != password) {
                        confirmPasswordError = true
                    }
                    if((!emailError) && (!passwordError) && (!confirmPasswordError)) {
                        if(!authScreenVM.createUser(email, password)) {
                            authenticationError = true
                        } else {
                            if(userCalorieData == null) {
                                navController.navigate(StartScreen) {
                                    popUpTo(0)
                                }
                            } else {
                                navController.navigate(HomeScreen) {
                                    popUpTo(0)
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
                text = "Create account"
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = authenticationError,
        ) {
            ErrorMessage(
                onTimeEnds = { authenticationError = false }
            )
        }
    }
}