package com.example.caloriecounter.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.caloriecounter.auth.google_auth.GoogleAuthUiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    scope: CoroutineScope = rememberCoroutineScope(),
    authScreenVM: AuthScreenVM,
    googleAuthUiClient: GoogleAuthUiClient
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Calorie Counter"
                ) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            val pagerState = rememberPagerState(pageCount = { SignTabs.entries.size })
            val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                SignTabs.entries.forEachIndexed { index, tab ->
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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { currentPage ->
                when(currentPage) {
                    0 -> SignTabs.entries[0].content(authScreenVM, googleAuthUiClient)
                    1 -> SignTabs.entries[1].content(authScreenVM, null)
                }
            }
        }
    }
}

enum class SignTabs(
    val text: String,
    val content: @Composable (
        authScreenVM: AuthScreenVM,
        googleAuthUiClient: GoogleAuthUiClient?
    ) -> Unit
) {
    SignIn(
        text = "Sign in",
        content = { authScreenVM, googleAuthUiClient -> SignInContent(authScreenVM, googleAuthUiClient!!) }
    ),
    SignUp(
        text = "Sign up",
        content = { authScreenVM, _ -> SignUpContent(authScreenVM) }
    )
}