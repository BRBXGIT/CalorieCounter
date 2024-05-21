package com.example.caloriecounter.home_screen.presentation

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
        tonalElevation = 20.dp
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
                        text = "Received",
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )) {
                                append(receivedAmount)
                            }
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )) {
                                append(" / ")
                            }
                            withStyle(SpanStyle(
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                fontSize = 16.sp
                            )) {
                                append("$requiredAmount kcal")
                            }
                        }
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Spent",
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )) {
                                append(spentAmount)
                            }
                            withStyle(SpanStyle(
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                fontSize = 16.sp
                            )) {
                                append(" kcal")
                            }
                        }
                    )
                }
            }

            var progress by rememberSaveable { mutableFloatStateOf(0f) }
            LaunchedEffect(key1 = requiredAmount) {
                progress = if(requiredAmount.toInt() != 0) {
                    totalAmount.toFloat() / requiredAmount.toFloat()
                } else {
                    0f
                }
            }
            CircleCalorieIndicator(
                size = 120.dp,
                backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                progressColor = MaterialTheme.colorScheme.primary,
                targetProgress = progress,
                totalAmount = totalAmount.toInt()
            )
        }
    }
}