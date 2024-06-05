package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal

@Composable
fun AllDishesContent(
    dishes: List<Meal>,
    eatingScreenVM: EatingScreenVM,
    selectedDate: String,
    todayCalorieData: DayCalorieData?,
    todayNutrientsData: List<Int>
) {

//    if (todayCalorieData != null) {
//        eatingScreenVM.updateTodayCalorieData(
//            date = selectedDate,
//            calorieAmount = todayCalorieData.receivedCaloriesAmount + dish.calories
//        )
//    }
//
//    dish.nutrients.forEachIndexed { index, nutrient ->
//        eatingScreenVM.updateTodayNutrientData(
//            nutrientId = nutrient.nutrientId,
//            amount = dish.nutrients[index].willReceiveAmount + todayNutrientsData[index],
//            date = selectedDate
//        )
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(dishes, key = { dish -> dish.id }) { dish ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .padding(16.dp)
                    .animateItem(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.6f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = dish.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${dish.measureInGram} g",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.4f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${dish.calories} kcal",
                        )

                        var openInfoDialog by rememberSaveable { mutableStateOf(false) }
                        if(openInfoDialog) {
                            DishInfoDialog(
                                dish = dish,
                                onDismissRequest = { openInfoDialog = false }
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable {
                                    openInfoDialog = true
                                },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_cross),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable {
                                    eatingScreenVM.deleteDishById(dish.id)
                                },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}