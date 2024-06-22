package com.example.caloriecounter.main_screens.screens.home_screen.nutrients_indicators_section

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.data.day_nutrient_data.nutrient.Nutrient
import com.example.caloriecounter.main_screens.screens.home_screen.HomeScreenVM
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.color.ColorDialog
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.MultipleColors
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
        shadowElevation = 1.dp,
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

            val nutrientColor = MultipleColors.ColorsInt(
                Color(0xfffc87bf).toArgb(),
                Color(0xfff8e38a).toArgb(),
                Color(0xff6fe5e9).toArgb(),
                Color(0xff5be4b2).toArgb(),
                Color(0xffb95cf4).toArgb()
            )
            var chosenColor by rememberSaveable { mutableLongStateOf(0xfffc87bf) }

            val colorDialogState = rememberUseCaseState()
            ColorDialog(
                state = colorDialogState,
                selection = ColorSelection(
                    onSelectColor = {
                        chosenColor = (it.toLong() and 0xffffffffL)
                    },
                ),
                config = ColorConfig(
                    templateColors = nutrientColor,
                ),
            )

            Button(
                onClick = {
                    colorDialogState.show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(100.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Choose color",
                    fontSize = 17.sp
                )
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