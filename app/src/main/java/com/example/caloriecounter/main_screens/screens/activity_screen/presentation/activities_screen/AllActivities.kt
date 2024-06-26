package com.example.caloriecounter.main_screens.screens.activity_screen.presentation.activities_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecounter.R
import com.example.caloriecounter.main_screens.screens.activity_screen.data.activity_db.Activity
import com.example.caloriecounter.main_screens.screens.activity_screen.presentation.ActivityScreenVM

@Composable
fun AllActivitiesContent(
    onActivityClick: () -> Unit = {},
    activities: List<Activity>,
    activityScreenVM: ActivityScreenVM,
    selectedDate: String,
    spentCalorieAmount: Int?
) {
    if(activities.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(activities, key = { activity -> activity.id }) { activity ->
                var openAddActivityBottomSheet by remember { mutableStateOf(false) }
                if(openAddActivityBottomSheet) {
                    AddActivityBottomSheet(
                        onDismissRequest = { openAddActivityBottomSheet = false },
                        activity = activity,
                        activityScreenVM = activityScreenVM,
                        selectedDate = selectedDate,
                        spentCaloriesAmount = spentCalorieAmount
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onActivityClick()
                            openAddActivityBottomSheet = true
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
                            text = activity.name,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = activity.time,
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
                                text = "-${activity.spentCalories} kcal",
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_cross),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        activityScreenVM.deleteActivity(activity.id)
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
    } else {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            while(!visible) {
                visible = true
            }
        }
        val animatedAlpha by animateFloatAsState(
            targetValue = if(visible) 1f else 0f,
            animationSpec = tween(1000),
            label = "Animated alpha"
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nothing here, add activity :)",
                modifier = Modifier
                    .graphicsLayer {
                        alpha = animatedAlpha
                    }
            )
        }
    }
}