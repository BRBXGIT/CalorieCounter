package com.example.caloriecounter.main_screens.screens.activity_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.MainScreensSharedVM
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import com.example.caloriecounter.main_screens.screens.main_screens_bars.bottom_bar.MainScreensBottomBar
import com.example.caloriecounter.main_screens.screens.main_screens_bars.top_bar.MainScreensTopBar
import com.example.caloriecounter.navigation.AddActivityScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    navController: NavHostController,
    mainScreensSharedVM: MainScreensSharedVM,
    activityScreenVM: ActivityScreenVM,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MainScreensBottomBar(navController = navController) },
        topBar = { MainScreensTopBar(mainScreensSharedVM = mainScreensSharedVM) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AddActivityScreen) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        val allActivities = activityScreenVM
            .getAllActivities()
            .collectAsState(initial = emptyList())
            .value

        val selectedDate = mainScreensSharedVM.selectedDate.value
        val spentCaloriesAmount = activityScreenVM
            .getSpentCalorieAmount(selectedDate)
            .collectAsState(initial = null)
            .value
            ?.spentCaloriesAmount

        val featuredActivities = allActivities.filter { it.featured }

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

            val activitiesBySearch = activityScreenVM
                .getActivitiesByName(query)
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
                AllActivitiesContent(
                    onActivityClick = { focusManager.clearFocus() },
                    activities = activitiesBySearch,
                    activityScreenVM = activityScreenVM,
                    selectedDate = selectedDate,
                    spentCalorieAmount = spentCaloriesAmount
                )
            }

            val pagerState = rememberPagerState(pageCount = { ActivityTabs.entries.size })
            val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                ActivityTabs.entries.forEachIndexed { index, tab ->
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
                    0 -> ActivityTabs.entries[0].content(
                        allActivities,
                        activityScreenVM,
                        selectedDate,
                        spentCaloriesAmount
                    )
                    1 -> ActivityTabs.entries[1].content(
                        featuredActivities,
                        activityScreenVM,
                        selectedDate,
                        spentCaloriesAmount
                    )
                }
            }
        }
    }
}

//Enum class with tabRows
enum class ActivityTabs(
    val text: String,
    val content: @Composable (
        activities: List<Activity>,
        activityScreenVM: ActivityScreenVM,
        selectedDate: String,
        spentCaloriesAmount: Int?
    ) -> Unit
) {
    AllActivities(
        text = "My activity",
        content = {
            activities, activityScreenVM, selectedDate, spentCaloriesAmount ->
            AllActivitiesContent(
                activities = activities,
                activityScreenVM = activityScreenVM,
                selectedDate = selectedDate,
                spentCalorieAmount = spentCaloriesAmount
            )
        }
    ),
    FeaturedActivities(
        text = "Featured",
        content = {
            activities, activityScreenVM, selectedDate, spentCaloriesAmount ->
            FeaturedActivitiesContent(
                activities = activities,
                activityScreenVM = activityScreenVM,
                selectedDate = selectedDate,
                spentCaloriesAmount = spentCaloriesAmount
            )
        }
    )
}