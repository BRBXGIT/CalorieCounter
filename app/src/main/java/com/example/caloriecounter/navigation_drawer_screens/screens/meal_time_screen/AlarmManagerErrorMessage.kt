package com.example.caloriecounter.navigation_drawer_screens.screens.meal_time_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.caloriecounter.R

@Composable
fun AlarmManagerErrorMessage(
    context: Context
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(MaterialTheme.colorScheme.error),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cross),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )

            val annotatedLinkString = buildAnnotatedString {
                val str = "Alarms are denied, please enable it here"
                val startIndex = str.indexOf("here")
                val endIndex = startIndex + 4
                append(str)
                addStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary
                    ), start = startIndex, end = endIndex
                )
                addStringAnnotation(
                    tag = "intent",
                    annotation = "enable_alarms",
                    start = startIndex,
                    end = endIndex
                )
            }

            ClickableText(
                text = annotatedLinkString,
                style = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                onClick = { offset ->
                annotatedLinkString.getStringAnnotations(tag = "intent", start = offset, end = offset).firstOrNull()?.let {
                    openExactAlarmSettings(context)
                }
            })
        }
    }
}

private fun openExactAlarmSettings(
    context: Context
) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = Uri.parse("package:com.example.caloriecounter")
        }
        context.startActivity(intent)
    }
}