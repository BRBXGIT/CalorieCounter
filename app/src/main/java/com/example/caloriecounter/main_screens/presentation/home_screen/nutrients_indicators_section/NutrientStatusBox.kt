package com.example.caloriecounter.main_screens.presentation.home_screen.nutrients_indicators_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NutrientStatusBox(
    name: String,
    color: Long,
    requiredAmount: Int,
    receivedAmount: Int
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 20.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .height(190.dp)
            .width(140.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    ) {
                        append(receivedAmount.toString())
                    }
                    withStyle(
                        SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    ) {
                        append(" / ")
                    }
                    withStyle(
                        SpanStyle(
                        fontSize = 15.sp
                    )
                    ) {
                        append("$requiredAmount g")
                    }
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            var progress by rememberSaveable { mutableFloatStateOf(0f) }
            LaunchedEffect(key1 = requiredAmount, key2 = receivedAmount) {
                progress = if(requiredAmount != 0) {
                    receivedAmount.toFloat() / requiredAmount.toFloat()
                } else {
                    0f
                }
            }
            CircleNutrientIndicator(
                color = Color(color),
                backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant,
                progress = progress,
                size = 76.dp
            )
        }
    }
}