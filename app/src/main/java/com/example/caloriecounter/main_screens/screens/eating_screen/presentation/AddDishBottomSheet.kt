package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDishBottomSheet(
    onDismissRequest: () -> Unit,
    dish: Meal,
    eatingScreenVM: EatingScreenVM,
    todayCalorieData: DayCalorieData?,
    todayNutrientsData: List<Int>,
    selectedDate: String
) {
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = state,
        tonalElevation = 0.dp
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
                            text = "Dishes",
                            fontSize = 14.sp
                        )

                        Text(
                            text = dish.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    if(dish.featured) {
                        IconButton(
                            onClick = {
                                eatingScreenVM.updateFeatureParameter(false, dish.id)
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
                                eatingScreenVM.updateFeatureParameter(true, dish.id)
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
                var quantityError by rememberSaveable { mutableStateOf(false) }

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
                    label = { Text(text = "Quantity") },
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = quantityError
                )

                Button(
                    onClick = {
                        if(quantity.isNotBlank()) {
                            if (todayCalorieData != null) {
                                eatingScreenVM.updateTodayCalorieData(
                                    date = selectedDate,
                                    calorieAmount = todayCalorieData.receivedCaloriesAmount + (dish.calories * quantity.toInt())
                                )
                            }

                            dish.nutrients.forEachIndexed { index, nutrient ->
                                eatingScreenVM.updateTodayNutrientData(
                                    nutrientId = nutrient.nutrientId,
                                    amount = (dish.nutrients[index].willReceiveAmount * quantity.toInt()) + todayNutrientsData[index],
                                    date = selectedDate
                                )
                            }

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
                        text = "Add dish"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}