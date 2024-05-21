package com.example.caloriecounter.home_screen.presentation

import android.graphics.CornerPathEffect
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleCalorieIndicator(
    size: Dp,
    backgroundColor: Color,
    progressBrush: Brush,
    totalAmount: Int,
    targetProgress: Float
) {
    val progressAnimation by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress animation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(12f)
            )
            drawArc(
                brush = progressBrush,
                startAngle = -90f,
                sweepAngle = 360 * progressAnimation,
                useCenter = false,
                style = Stroke(18f, cap = StrokeCap.Round)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Total",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
            Text(
                text = "$totalAmount",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "kcal",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    }
}