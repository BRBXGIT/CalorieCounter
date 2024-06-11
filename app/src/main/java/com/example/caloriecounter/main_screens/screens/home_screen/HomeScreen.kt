package com.example.caloriecounter.main_screens.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.caloriecounter.app.data.user_calorie_db.UserCalorieData
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.home_screen.calorie_indicator_section.CalorieIndicatorSection
import com.example.caloriecounter.main_screens.screens.home_screen.drinking_section.DrinkingSection
import com.example.caloriecounter.main_screens.screens.home_screen.nutrients_indicators_section.AddNutrient
import com.example.caloriecounter.main_screens.screens.home_screen.nutrients_indicators_section.NutrientStatusBox
import com.example.caloriecounter.main_screens.screens.main_screens_bars.bottom_bar.MainScreensBottomBar
import com.example.caloriecounter.main_screens.screens.main_screens_bars.top_bar.MainScreensTopBar

@Composable
fun HomeScreen(
    homeScreenVM: HomeScreenVM,
    mainScreensSharedVM: MainScreensSharedVM,
    navController: NavHostController
) {
    val selectedDate by rememberSaveable { mainScreensSharedVM.selectedDate }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {

            }
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MainScreensTopBar(
                mainScreensSharedVM = mainScreensSharedVM,
                drawerState = drawerState
            ) },
            bottomBar = { MainScreensBottomBar(navController = navController) }
        ) { innerPadding ->

            val userRequirementCalorie = homeScreenVM
                .getUserRequirementCalorieData()
                .collectAsState(initial = UserCalorieData(
                    id = 0,
                    requiredCalorieAmount = 0,
                    requiredWaterAmount = 0,
                    weight = 0,
                    height = 0
                ))
                .value
            val todayCalorieData = homeScreenVM
                .getDayCalorieData(selectedDate)
                .collectAsState(initial = null)
                .value
            if(todayCalorieData == null) {
                homeScreenVM.upsertNewDayCalorieData(selectedDate)
            }

            val mainColumnScrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .verticalScroll(mainColumnScrollState),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Spacer(modifier = Modifier.height(0.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Indicators calories",
                        modifier = Modifier.padding(start = 14.dp),
                        fontSize = 17.sp
                    )

                    val requiredAmount = userRequirementCalorie.requiredCalorieAmount
                    var receivedAmount by rememberSaveable { mutableIntStateOf(0) }
                    var spentAmount by rememberSaveable { mutableIntStateOf(0) }
                    var totalAmount by rememberSaveable { mutableIntStateOf(0) }
                    if(todayCalorieData != null) {
                        receivedAmount = todayCalorieData.receivedCaloriesAmount
                        spentAmount = todayCalorieData.spentCaloriesAmount
                        totalAmount = (receivedAmount - spentAmount)
                    }
                    CalorieIndicatorSection(
                        receivedAmount = receivedAmount.toString(),
                        requiredAmount = requiredAmount.toString(),
                        spentAmount = spentAmount.toString(),
                        totalAmount = totalAmount.toString()
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Trace element indicators",
                        modifier = Modifier.padding(start = 14.dp),
                        fontSize = 17.sp
                    )

                    val nutrients = homeScreenVM
                        .getAllNutrients()
                        .collectAsState(initial = emptyList())
                        .value
                    //Checking if date was changed or user insert new nutrient
                    //and after this create row in db with new date and nutrient
                    LaunchedEffect(key1 = nutrients, key2 = selectedDate) {
                        nutrients.forEach { nutrient ->
                            homeScreenVM.insertNewDayNutrientAmount(nutrient.nutrientId, selectedDate)
                        }
                    }
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            AddNutrient(homeScreenVM)
                        }

                        items(nutrients) { nutrient ->
                            val receivedNutrientAmount = homeScreenVM
                                .getNutrientAmountByDate(nutrient.nutrientId, selectedDate)
                                .collectAsState(initial = null)
                                .value
                            NutrientStatusBox(
                                name = nutrient.name,
                                color = nutrient.color,
                                requiredAmount = nutrient.requiredAmount,
                                receivedAmount = receivedNutrientAmount ?: 0
                            )
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Drinking mode",
                        modifier = Modifier.padding(start = 14.dp),
                        fontSize = 17.sp
                    )

                    val requiredWaterAmount = userRequirementCalorie.requiredWaterAmount
                    var receivedWaterAmount by rememberSaveable { mutableIntStateOf(0) }
                    var lastDrinkAt by rememberSaveable { mutableStateOf("") }
                    if(todayCalorieData != null) {
                        receivedWaterAmount = todayCalorieData.receivedWaterAmount
                        lastDrinkAt = todayCalorieData.lastDrinkAt
                    }
                    DrinkingSection(
                        requiredWaterAmount = requiredWaterAmount,
                        receivedWaterAmount = receivedWaterAmount,
                        lastDrinkAt = lastDrinkAt,
                        homeScreenVM = homeScreenVM,
                        selectedDate = selectedDate
                    )
                }

                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}
