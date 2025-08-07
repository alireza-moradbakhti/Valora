package com.example.valora.presentation.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.valora.R
import com.example.valora.presentation.ui.components.ConversionCard
import com.example.valora.presentation.ui.components.CurrencyBottomSheet
import com.example.valora.presentation.ui.components.ErrorState
import com.example.valora.presentation.utils.MainScreenEvent
import com.example.valora.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isChangingFromCurrency by remember { mutableStateOf(true) }

    // Show the bottom sheet when needed
    if (showBottomSheet) {
        val currencyList = remember(state.conversionRates) {
            state.conversionRates?.rates?.keys?.sorted() ?: emptyList()
        }
        CurrencyBottomSheet(
            availableCurrencies = currencyList,
            onCurrencySelected = { currency ->
                if (isChangingFromCurrency) {
                    viewModel.onEvent(MainScreenEvent.FromCurrencyChanged(currency))
                } else {
                    viewModel.onEvent(MainScreenEvent.ToCurrencyChanged(currency))
                }
                showBottomSheet = false
            },
            onDismiss = { showBottomSheet = false }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.galaxy_background),
            contentDescription = "Galaxy background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Valora") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val errorMessage = state.error
                if (state.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
                } else if (state.conversionRates != null) {
                    ConversionCard(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onFromCurrencyClick = {
                            isChangingFromCurrency = true
                            showBottomSheet = true
                        },
                        onToCurrencyClick = {
                            isChangingFromCurrency = false
                            showBottomSheet = true
                        }
                    )
                } else if (errorMessage != null) {
                    ErrorState(
                        errorMessage = errorMessage,
                        onRetry = { viewModel.onEvent(MainScreenEvent.Retry) }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = !state.isOnline,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Offline",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "You are currently offline",
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}
