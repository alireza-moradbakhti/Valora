package com.example.valora.presentation.utils

import com.example.valora.domain.model.ConversionRates

/**
 * The state holder for our main screen. It contains all the data
 * necessary to render the UI.
 */
data class MainScreenState(
    val isLoading: Boolean = true, // Start with loading true
    val fromCurrency: String = "USD",
    val toCurrency: String = "EUR",
    val amount: String = "1.00",
    // Store the whole ConversionRates object, not just the keys
    val conversionRates: ConversionRates? = null,
    val conversionResult: String? = null,
    val error: String? = null,
    val isOnline: Boolean = true // state to track connectivity
)
