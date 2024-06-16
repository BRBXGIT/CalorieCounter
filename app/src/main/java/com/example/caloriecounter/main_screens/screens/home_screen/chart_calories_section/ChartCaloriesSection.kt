package com.example.caloriecounter.main_screens.screens.home_screen.chart_calories_section

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.main_screens.data.day_calorie_data.DayCalorieData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ChartCaloriesSection(
    allCalorieData: List<DayCalorieData>,
    requiredCalorieAmount: Int,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Calories statistics",
                modifier = Modifier.padding(16.dp)
            )
            val basicFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
            val sortedCalorieData = allCalorieData.sortedBy {
                LocalDate.parse(it.date, basicFormatter)
            }

            val lazyRowState = rememberLazyListState()
            val todayDate = LocalDateTime.now().format(basicFormatter)
            val todayIndex = sortedCalorieData.indexOfFirst {
                it.date == todayDate
            }

            LaunchedEffect(todayIndex) {
                if(todayIndex != -1) {
                    lazyRowState.scrollToItem(todayIndex)
                }
            }
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp
                ),
                modifier = Modifier.padding(bottom = 16.dp),
                state = lazyRowState
            ) {
                items(sortedCalorieData, key = { data -> data.date }) { data ->
                    val uiFormatter = DateTimeFormatter.ofPattern("dd.MM")
                    val date = LocalDate.parse(data.date, basicFormatter)
                    val outputDateString = date.format(uiFormatter)

                    var progress by rememberSaveable { mutableFloatStateOf(0f) }
                    LaunchedEffect(key1 = requiredCalorieAmount, key2 = data.receivedCaloriesAmount) {
                        progress = if(requiredCalorieAmount == 0) {
                            0f
                        } else {
                            (data.receivedCaloriesAmount - data.spentCaloriesAmount).toFloat() / requiredCalorieAmount.toFloat()
                        }
                    }
                    val percentFromRequired by animateFloatAsState(
                        targetValue = progress,
                        label = "progress animation",
                        animationSpec = tween(1000)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.animateItem()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .width(20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.surfaceDim),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(percentFromRequired)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary
                                            )
                                        )
                                    )
                            )
                        }

                        Text(
                            text = outputDateString,
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
    }
}