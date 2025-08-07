package com.example.valora.presentation.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.valora.presentation.ui.theme.glassmorphism
import com.example.valora.presentation.utils.MainScreenEvent
import com.example.valora.presentation.utils.MainScreenState

@Composable
fun ConversionCard(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit,
    onFromCurrencyClick: () -> Unit,
    onToCurrencyClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var rotated by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "swapIconRotation"
    )

    val targetResult = state.conversionResult?.toFloatOrNull() ?: 0f
    val animatedResult by animateFloatAsState(
        targetValue = targetResult,
        animationSpec = tween(durationMillis = 800),
        label = "resultCountUp"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .glassmorphism(
                cornerRadius = 24.dp,
                blurRadius = 16.dp,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                borderWidth = 1.5.dp
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CurrencyRow(
            currencyCode = state.fromCurrency,
            onCurrencyClick = onFromCurrencyClick,
            amount = state.amount,
            onAmountChange = { newAmount ->
                onEvent(MainScreenEvent.AmountChanged(newAmount))
            }
        )

        IconButton(onClick = {
            rotated = !rotated
            onEvent(MainScreenEvent.SwapCurrencies)
        }) {
            Icon(
                Icons.Default.SwapVert,
                contentDescription = "Swap currencies",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.rotate(rotationAngle)
            )
        }

        CurrencyRow(
            currencyCode = state.toCurrency,
            onCurrencyClick = {
                keyboardController?.hide()
                onToCurrencyClick()
            }
        )

        AnimatedVisibility(
            visible = state.conversionResult != null,
            enter = fadeIn() + slideInVertically { it / 2 }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${state.amount} ${state.fromCurrency} =",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Text(
                    text = "%.2f %s".format(animatedResult, state.toCurrency),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
