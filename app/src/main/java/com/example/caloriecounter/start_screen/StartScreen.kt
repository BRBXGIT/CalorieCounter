package com.example.caloriecounter.start_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    startScreenVM: StartScreenVM
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Thank you for registering!",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Before you start we need to calculate your parameters",
                textAlign = TextAlign.Center
            )

            var weight by rememberSaveable { mutableStateOf("") }
            weight = weight.filter { it.isDigit() }
            var height by rememberSaveable { mutableStateOf("") }
            height = height.filter { it.isDigit() }
            var age by rememberSaveable { mutableStateOf("") }
            age = age.filter { it.isDigit() }
            var sex by rememberSaveable { mutableStateOf("Male") }
            var activity by rememberSaveable { mutableDoubleStateOf(1.2) }

            var weightError by rememberSaveable { mutableStateOf(false) }
            var heightError by rememberSaveable { mutableStateOf(false) }
            var ageError by rememberSaveable { mutableStateOf(false) }

            LaunchedEffect(
                key1 = weightError,
                key2 = heightError,
                key3 = ageError
            ) {
                delay(3000)
                weightError = false
                heightError = false
                ageError = false
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text(text = "Enter your weight") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                        ),
                        isError = weightError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text(text = "Enter your height") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                        ),
                        isError = heightError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text(text = "Enter your age") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                    ),
                    isError = ageError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                var isExpandedActivityMenu by rememberSaveable { mutableStateOf(false) }
                var activityType by rememberSaveable { mutableStateOf("Absent or minimal activity") }
                ExposedDropdownMenuBox(
                    expanded = isExpandedActivityMenu,
                    onExpandedChange = { isExpandedActivityMenu = it },
                ) {
                    TextField(
                        value = activityType,
                        onValueChange = { activityType = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                        ),
                        readOnly = true,
                        maxLines = 1,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                    )

                    ExposedDropdownMenu(
                        expanded = isExpandedActivityMenu,
                        onDismissRequest = { isExpandedActivityMenu = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Absent or minimal activity") },
                            onClick = {
                                activity = 1.2
                                activityType = "Absent or minimal activity"
                                isExpandedActivityMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "3 moderate severity workout per week") },
                            onClick = {
                                activity = 1.38
                                activityType = "3 moderate severity workout per week"
                                isExpandedActivityMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "5 moderate severity workout per week") },
                            onClick = {
                                activity = 1.46
                                activityType = "5 moderate severity workout per week"
                                isExpandedActivityMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "5 intensive workout per week") },
                            onClick = {
                                activity = 1.55
                                activityType = "5 intensive workout per week"
                                isExpandedActivityMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Everyday workout") },
                            onClick = {
                                activity = 1.64
                                activityType = "Everyday workout"
                                isExpandedActivityMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Everyday intensive workout") },
                            onClick = {
                                activity = 1.79
                                activityType = "Everyday intensive workout"
                                isExpandedActivityMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Everyday intensive workout + physical work") },
                            onClick = {
                                activity = 1.9
                                activityType = "Everyday intensive workout + physical work"
                                isExpandedActivityMenu = false
                            }
                        )
                    }
                }

                var isExpandedSexMenu by rememberSaveable { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = isExpandedSexMenu,
                    onExpandedChange = { isExpandedSexMenu = it },
                ) {
                    TextField(
                        value = sex,
                        onValueChange = { sex = it },
                        modifier = Modifier
                            .width(200.dp)
                            .menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                        ),
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = isExpandedSexMenu,
                        onDismissRequest = { isExpandedSexMenu = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Male") },
                            onClick = {
                                sex = "Male"
                                isExpandedSexMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Female") },
                            onClick = {
                                sex = "Female"
                                isExpandedSexMenu = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "What you want to get?",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            var userTarget by rememberSaveable { mutableIntStateOf(0) }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                UserTarget(
                    isSelected = userTarget == 0,
                    text = "Lose weight"
                ) {
                    userTarget = 0
                }
                UserTarget(
                    isSelected = userTarget == 1,
                    text = "Gain weight"
                ) {
                    userTarget = 1
                }
                UserTarget(
                    isSelected = userTarget == 2,
                    text = "See my calorie amount"
                ) {
                    userTarget = 2
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if(weight.isBlank()) {
                        weightError = true
                    }
                    if(height.isBlank()) {
                        heightError = true
                    }
                    if(age.isBlank()) {
                        ageError = true
                    }
                    if((!ageError) && (!heightError) && (!weightError)) {
                        startScreenVM.storeCalculatedUserCalorie(
                            weight = weight.toInt(),
                            age = age.toInt(),
                            height = height.toInt(),
                            activityCoefficient = activity,
                            sex = sex,
                            target = userTarget
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
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
                    text = "LET'S START"
                )
            }
        }
    }
}