package com.example.caloriecounter.main_screens.screens.eating_screen.presentation

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
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddDishScreen(
    eatingScreenVM: EatingScreenVM
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val nutrients = eatingScreenVM
            .getAllNutrients()
            .collectAsState(initial = emptyList())
            .value

        val columnScroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(columnScroll),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") }
            )

            var calorieAmount by rememberSaveable { mutableStateOf("") }
            calorieAmount = calorieAmount.filter { it.isDigit() }
            OutlinedTextField(
                value = calorieAmount,
                onValueChange = { calorieAmount = it },
                label = { Text(text = "Calories amount") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            var measure by rememberSaveable { mutableStateOf("") }
            measure = measure.filter { it.isDigit() }
            OutlinedTextField(
                value = measure,
                onValueChange = { measure = it },
                label = { Text(text = "Measure(grams)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            for(nutrient in nutrients) {
                var nutrientAmount by rememberSaveable { mutableStateOf("") }
                nutrientAmount = nutrientAmount.filter { it.isDigit() }
                OutlinedTextField(
                    value = nutrientAmount,
                    onValueChange = { nutrientAmount = it },
                    label = { Text(text = "${nutrient.name} amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add dish")
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}