package com.example.caloriecounter.main_screens.screens.home_screen.calorie_indicator_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalorieIndicatorSection(
    receivedAmount: String,
    requiredAmount: String,
    spentAmount: String,
    totalAmount: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(164.dp)
            .padding(start = 16.dp, end = 16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Received"
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )) {
                                append(receivedAmount)
                            }
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                            )) {
                                append(" / ")
                            }
                            withStyle(SpanStyle(
                                fontSize = 16.sp,
                            )) {
                                append("$requiredAmount kcal")
                            }
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Spent",
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )) {
                                append(spentAmount)
                            }
                            withStyle(SpanStyle(
                                fontSize = 16.sp
                            )) {
                                append(" kcal")
                            }
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            var progress by rememberSaveable { mutableFloatStateOf(0f) }
            LaunchedEffect(key1 = requiredAmount, key2 = totalAmount) {
                progress = if(requiredAmount.toInt() != 0) {
                    totalAmount.toFloat() / requiredAmount.toFloat()
                } else {
                    0f
                }
            }
            CircleCalorieIndicator(
                size = 120.dp,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                targetProgress = progress,
                totalAmount = totalAmount.toInt(),
                progressBrush = Brush.horizontalGradient(listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.primary
                ))
            )
        }
    }
}