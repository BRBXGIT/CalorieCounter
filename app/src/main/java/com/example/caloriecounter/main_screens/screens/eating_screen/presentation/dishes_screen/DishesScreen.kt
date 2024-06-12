package com.example.caloriecounter.main_screens.screens.eating_screen.presentation.dishes_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.eating_screen.data.meal_db.Meal
import com.example.caloriecounter.main_screens.screens.eating_screen.presentation.EatingScreenVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishesScreen(
    typeOfDish: String,
    eatingScreenVM: EatingScreenVM,
    navController: NavHostController,
    mainScreensSharedVM: MainScreensSharedVM,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = typeOfDish
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

        val featuredDishes = dishes.filter { it.featured }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            var query by rememberSaveable { mutableStateOf("") }
            var active by rememberSaveable { mutableStateOf(false) }

            val dishesBySearch = eatingScreenVM
                .getDishByName(query, typeOfDish)
                .collectAsState(initial = emptyList())
                .value

            val focusManager = LocalFocusManager.current
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    focusManager.clearFocus()
                },
                active = active,
                onActiveChange = { active = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if(active) {
                        IconButton(
                            onClick = { active = false }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cross),
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                windowInsets = WindowInsets(0, 0, 0, 0),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth(),
                tonalElevation = 0.dp
            ) {
                AllDishesContent(
                    onDishClick = { focusManager.clearFocus() },
                    dishes = dishesBySearch,
                    eatingScreenVM = eatingScreenVM,
                    selectedDate = selectedDate,
                    todayCalorieData = todayCalorieData,
                    todayNutrientsData = todayNutrientsData
                )
            }

            val pagerState = rememberPagerState(pageCount = { DishesTabs.entries.size })
            val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                DishesTabs.entries.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(tab.ordinal)
                            }
                        },
                        text = { Text(text = tab.text) },
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { currentPage ->
                when(currentPage) {
                    0 -> DishesTabs.entries[0].content(
                        dishes,
                        eatingScreenVM,
                        selectedDate,
                        todayCalorieData,
                        todayNutrientsData
                    )
                    1 -> DishesTabs.entries[1].content(
                        featuredDishes,
                        eatingScreenVM,
                        selectedDate,
                        todayCalorieData,
                        todayNutrientsData
                    )
                }
            }
        }
    }
}

//Enum class with tabRows
enum class DishesTabs(
    val text: String,
    val content: @Composable (
        dishes: List<Meal>,
        eatingScreenVM: EatingScreenVM,
        selectedDate: String,
        todayCalorieData: DayCalorieData?,
        todayNutrientsData: List<Int>
    ) -> Unit
) {
    AllDishes(
        text = "My dishes",
        content = {
            dishes, eatingScreenVM, selectedDate, todayCalorieData, todayNutrientsData ->
            AllDishesContent(
                dishes = dishes,
                eatingScreenVM = eatingScreenVM,
                selectedDate = selectedDate,
                todayCalorieData = todayCalorieData,
                todayNutrientsData = todayNutrientsData
            )
        }
    ),
    FeaturedDishes(
        text = "Featured",
        content = {
            dishes, eatingScreenVM, selectedDate, todayCalorieData, todayNutrientsData ->
            FeaturedDishesContent(
                dishes = dishes,
                eatingScreenVM = eatingScreenVM,
                selectedDate = selectedDate,
                todayCalorieData = todayCalorieData,
                todayNutrientsData = todayNutrientsData
            )
        }
    )
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
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}