package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishesScreen(
    typeOfDish: String,
    eatingScreenVM: EatingScreenVM,
    navController: NavHostController,
    mainScreensSharedVM: MainScreensSharedVM
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Add food"
                ) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_navigation_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val selectedDate = mainScreensSharedVM.selectedDate.value

        val dishes = eatingScreenVM
            .getAllDishes()
            .collectAsState(initial = emptyList())
            .value
            .filter {
                it.type == typeOfDish
            }

        val todayCalorieData = eatingScreenVM
            .getTodayCalorieData(selectedDate)
            .collectAsState(initial = null)
            .value

        val nutrients = eatingScreenVM
            .getAllNutrients()
            .collectAsState(initial = emptyList())
            .value
        var todayNutrientsData = listOf<Int>().toMutableList()

        nutrients.forEach { nutrient ->
            val receivedAmount = eatingScreenVM
                .getTodayNutrientsData(nutrient.nutrientId, selectedDate)
                .collectAsState(initial = null)
                .value
            if(receivedAmount != null) {
                todayNutrientsData = (todayNutrientsData + receivedAmount).toMutableList()
            }
        }

        Log.d("XXXX", todayNutrientsData.toString())

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            items(dishes, key = { dish -> dish.id }) { dish ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if(todayCalorieData != null) {
                                eatingScreenVM.updateTodayCalorieData(
                                    date = mainScreensSharedVM.selectedDate.value,
                                    calorieAmount = todayCalorieData.receivedCaloriesAmount + dish.calories
                                )
                            }
                            dish.nutrients.forEachIndexed { index, nutrient ->
                                eatingScreenVM.updateTodayNutrientData(
                                    nutrientId = nutrient.nutrientId,
                                    amount =  dish.nutrients[index].willReceiveAmount + todayNutrientsData[index],
                                    date = selectedDate
                                )
                            }
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
}

@Composable
fun DishInfoDialog(
    dish: Meal,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                ) {
                    Text(
                        text = "OK"
                    )
                }
            }
        },
        tonalElevation = 0.dp,
        title = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(dish.nutrients) { nutrient ->
                    Text(
                        text = "${nutrient.name}: ${nutrient.willReceiveAmount}",
                        fontSize = 15.sp
                    )
                }
            }
        }
    )
}