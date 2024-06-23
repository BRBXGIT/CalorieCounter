package com.example.caloriecounter.main_screens.screens.activity_screen.presentation.activities_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.ActivityScreenVM
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityBottomSheet(
    onDismissRequest: () -> Unit,
    activity: Activity,
    activityScreenVM: ActivityScreenVM,
    selectedDate: String,
    spentCaloriesAmount: Int?
) {
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = state,
        tonalElevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Activities",
                            fontSize = 14.sp
                        )

                        Text(
                            text = activity.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    if(activity.featured) {
                        IconButton(
                            onClick = {
                                activityScreenVM.updateActivityFeatureStatus(false, activity.id)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star_filled),
                                contentDescription = null,
                                tint = Color(0xffeadc90),
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                activityScreenVM.updateActivityFeatureStatus(true, activity.id)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star_outlined),
                                contentDescription = null,
                                tint = Color(0xffeadc90),
                            )
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var quantity by rememberSaveable { mutableStateOf("1") }
                quantity = quantity.filter { it.isDigit() }
                var quantityError by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = quantityError) {
                    delay(2000)
                    quantityError = false
                }

                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .weight(0.5f),
                    label = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Quantity")
                        }
                    },
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = quantityError,
                    maxLines = 1,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                )

                Button(
                    onClick = {
                        if(quantity.isNotBlank()) {
                            activityScreenVM.updateSpentCaloriesByDate(
                                date = selectedDate,
                                calorieAmount = (activity.spentCalories * quantity.toInt()) + spentCaloriesAmount!!
                            )
                            onDismissRequest()
                        } else {
                            quantityError = true
                        }
                    },
                    modifier = Modifier
                        .weight(0.7f)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Add activity"
                    )
                }
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}