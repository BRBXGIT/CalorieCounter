package com.example.caloriecounter.main_screens.screens.home_screen.nutrients_indicators_section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreenVM
import kotlinx.coroutines.delay

@Composable
fun AddNutrient(
    homeScreenVM: HomeScreenVM
) {
    var openAddNutrientSheet by rememberSaveable { mutableStateOf(false) }
    if(openAddNutrientSheet) {
        AddNutrientBottomSheet(
            onDismissRequest = { openAddNutrientSheet = false },
            homeScreenVM = homeScreenVM
        )
    }
    Surface(
        onClick = { openAddNutrientSheet = true },
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 20.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .height(190.dp)
            .width(140.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Add trace element",
                    textAlign = TextAlign.Center
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNutrientBottomSheet(
    onDismissRequest: () -> Unit,
    homeScreenVM: HomeScreenVM
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
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var name by rememberSaveable { mutableStateOf("") }
            var nameError by rememberSaveable { mutableStateOf(false) }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Name") },
                maxLines = 1,
                isError = nameError
            )

            var requiredAmount by rememberSaveable { mutableStateOf("") }
            requiredAmount = requiredAmount.filter { it.isDigit() }
            var requiredAmountError by rememberSaveable { mutableStateOf(false) }
            OutlinedTextField(
                value = requiredAmount,
                onValueChange = { requiredAmount = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Amount") },
                maxLines = 1,
                isError = requiredAmountError,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            val nutrientColor = listOf(
                0xfffc87bf, 0xfff8e38a,
                0xff6fe5e9, 0xff5be4b2,
                0xffb95cf4
            )
            var chosenColor by rememberSaveable { mutableLongStateOf(nutrientColor[0]) }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                items(nutrientColor) { color ->
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .alpha(
                                if (chosenColor == color) {
                                    1f
                                } else {
                                    0.3f
                                }
                            )
                            .background(Color(color))
                            .clickable {
                                chosenColor = color
                            }
                    )
                }
            }

            Button(
                onClick = {
                    if(requiredAmount.isBlank()) {
                        requiredAmountError = true
                    }
                    if(name.isBlank()) {
                        nameError = true
                    }
                    if((!nameError) && (!requiredAmountError)) {
                        homeScreenVM.upsertNutrient(Nutrient(
                            name = name,
                            requiredAmount = requiredAmount.toInt(),
                            color = chosenColor
                        ))
                        onDismissRequest()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Add nutrient",
                    fontSize = 17.sp
                )
            }

            LaunchedEffect(key1 = nameError, key2 = requiredAmountError) {
                delay(3000)
                nameError = false
                requiredAmountError = false
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}