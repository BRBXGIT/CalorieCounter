package com.example.caloriecounter.main_screens.screens.home_screen.drinking_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreenVM
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DrinkingSection(
    requiredWaterAmount: Int,
    receivedWaterAmount: Int,
    lastDrinkAt: String,
    homeScreenVM: HomeScreenVM,
    selectedDate: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(164.dp)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Received")

                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )) {
                                append(receivedWaterAmount.toString())
                            }
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                            )) {
                                append(" / ")
                            }
                            withStyle(SpanStyle(
                                fontSize = 16.sp
                            )) {
                                append("$requiredWaterAmount ml")
                            }
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock_filled),
                        contentDescription = null
                    )

                    Text(
                        text = "Last drank: $lastDrinkAt",
                        fontSize = 16.sp
                    )
                }
            }

            var progress by rememberSaveable { mutableFloatStateOf(0f) }
            LaunchedEffect(key1 = receivedWaterAmount, key2 = requiredWaterAmount) {
                progress = if(requiredWaterAmount != 0) {
                    receivedWaterAmount.toFloat() / requiredWaterAmount.toFloat()
                } else {
                    0f
                }
            }
            var addWaterDialogOpen by rememberSaveable { mutableStateOf(false) }
            var reduceWaterAmountDialogOpen by rememberSaveable { mutableStateOf(false) }
            if(addWaterDialogOpen) {
                AddWaterAmountDialog(
                    homeScreenVM = homeScreenVM,
                    onDismissRequest = { addWaterDialogOpen = false },
                    currentWaterAmount = receivedWaterAmount,
                    selectedDate = selectedDate
                )
            }
            if(reduceWaterAmountDialogOpen) {
                ReduceWaterAmountDialog(
                    homeScreenVM = homeScreenVM,
                    currentWaterAmount = receivedWaterAmount,
                    selectedDate = selectedDate,
                    onDismissRequest = { reduceWaterAmountDialogOpen = false }
                )
            }
            WaterBarIndicator(
                progress = progress,
                onAddButtonClick = { addWaterDialogOpen = true },
                onReduceButtonClick = { reduceWaterAmountDialogOpen = true }
            )
        }
    }
}

@Composable
fun AddWaterAmountDialog(
    homeScreenVM: HomeScreenVM,
    currentWaterAmount: Int,
    selectedDate: String,
    onDismissRequest: () -> Unit
) {
    var waterAmount by rememberSaveable { mutableStateOf("") }
    waterAmount = waterAmount.filter { it.isDigit() }
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Input water amount",
                    color = MaterialTheme.colorScheme.onPrimary
                )

                var amountError by rememberSaveable { mutableStateOf(false) }
                LaunchedEffect(key1 = amountError) {
                    delay(3000)
                    amountError = false
                }
                TextField(
                    value = waterAmount,
                    onValueChange = { waterAmount = it },
                    label = { Text(text = "Amount") },
                    isError = amountError,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Dismiss")
                    }

                    val timeOfDrink = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val currentTime = timeOfDrink.format(formatter)
                    TextButton(
                        onClick = {
                            if(waterAmount.isBlank()) {
                                amountError = true
                            } else {
                                homeScreenVM.updateDayReceivedWaterAmount(
                                    selectedDate, currentWaterAmount + waterAmount.toInt(), currentTime
                                )
                                onDismissRequest()
                            }
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun ReduceWaterAmountDialog(
    homeScreenVM: HomeScreenVM,
    currentWaterAmount: Int,
    selectedDate: String,
    onDismissRequest: () -> Unit
) {
    var waterAmount by rememberSaveable { mutableStateOf("") }
    waterAmount = waterAmount.filter { it.isDigit() }
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Input water amount",
                    color = MaterialTheme.colorScheme.onPrimary
                )

                var amountError by rememberSaveable { mutableStateOf(false) }
                LaunchedEffect(key1 = amountError) {
                    delay(3000)
                    amountError = false
                }
                TextField(
                    value = waterAmount,
                    onValueChange = { waterAmount = it },
                    label = { Text(text = "Amount") },
                    isError = amountError,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Dismiss")
                    }

                    val timeOfDrink = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val currentTime = timeOfDrink.format(formatter)
                    TextButton(
                        onClick = {
                            if(waterAmount.isBlank()) {
                                amountError = true
                            } else {
                                homeScreenVM.updateDayReceivedWaterAmount(
                                    selectedDate, currentWaterAmount - waterAmount.toInt(), currentTime
                                )
                                onDismissRequest()
                            }
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}