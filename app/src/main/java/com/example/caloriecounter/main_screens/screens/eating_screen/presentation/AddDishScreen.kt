package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDishScreen(
    eatingScreenVM: EatingScreenVM,
    navController: NavHostController,
    typeOfDish: String
) {
    var name by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }

    var calorieAmount by rememberSaveable { mutableStateOf("") }
    calorieAmount = calorieAmount.filter { it.isDigit() }
    var calorieAmountError by rememberSaveable { mutableStateOf(false) }

    var measure by rememberSaveable { mutableStateOf("") }
    measure = measure.filter { it.isDigit() }
    var measureError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = nameError, key2 = calorieAmountError, key3 = measureError) {
        delay(3000)
        nameError = false
        calorieAmountError = false
        measureError = false
    }

    val nutrients = eatingScreenVM
        .getAllNutrients()
        .collectAsState(initial = emptyList())
        .value
    val nutrientsValues = nutrients.toMutableList()
    LaunchedEffect(key1 = nutrientsValues) {
        Log.d("XXXX", nutrientsValues.toString())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add dish")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_navigation_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = { BottomAppBar(
            tonalElevation = 0.dp,
            modifier = Modifier.height(78.dp)
        ) {
            Button(
                onClick = {
                    if(calorieAmount.isBlank()) {
                        calorieAmountError = true
                    }
                    if(measure.isBlank()) {
                        measureError = true
                    }
                    if(name.isBlank()) {
                        nameError = true
                    }
                    if((!measureError) && (!calorieAmountError) && (!nameError)) {
                        eatingScreenVM.addDish(Meal(
                            name = name,
                            calories = calorieAmount.toInt(),
                            nutrients = nutrientsValues,
                            measureInGram = measure.toInt(),
                            type = typeOfDish
                        ))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Add dish")
            }
        } }
    ) { innerPadding ->
        val columnScroll = rememberScrollState()
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(columnScroll),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError,
                maxLines = 1
            )

            OutlinedTextField(
                value = calorieAmount,
                onValueChange = { calorieAmount = it },
                label = { Text(text = "Calories amount") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = calorieAmountError,
                maxLines = 1
            )

            OutlinedTextField(
                value = measure,
                onValueChange = { measure = it },
                label = { Text(text = "Measure(grams)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = measureError,
                maxLines = 1
            )

            nutrients.forEachIndexed { index, nutrient ->
                var amount by rememberSaveable { mutableStateOf("") }
                amount = amount.filter { it.isDigit() }
                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        nutrientsValues[index] = Nutrient(
                            nutrient.nutrientId,
                            name = nutrient.name,
                            requiredAmount = nutrient.requiredAmount,
                            willReceiveAmount = if(amount.toIntOrNull() == null) {
                                0
                            } else {
                                amount.toInt()
                            },
                            color = nutrient.color
                        )
                    },
                    label = { Text(
                        text = "${nutrient.name} amount",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    ) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    isError = measureError,
                )
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}