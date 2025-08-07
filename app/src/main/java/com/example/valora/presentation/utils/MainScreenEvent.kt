package com.example.valora.presentation.utils

sealed interface MainScreenEvent {
    data class AmountChanged(val amount: String) : MainScreenEvent
    data class FromCurrencyChanged(val currency: String) : MainScreenEvent
    data class ToCurrencyChanged(val currency: String) : MainScreenEvent
    object SwapCurrencies : MainScreenEvent
    object Retry : MainScreenEvent
}