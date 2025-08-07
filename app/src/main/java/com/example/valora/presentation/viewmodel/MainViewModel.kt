package com.example.valora.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valora.domain.usecase.GetConversionRatesUseCase
import com.example.valora.presentation.utils.MainScreenEvent
import com.example.valora.presentation.utils.MainScreenState
import com.example.valora.util.ConnectivityObserver
import com.example.valora.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for the main currency conversion screen.
 * It's responsible for fetching data, handling user input, and managing the UI state.
 *
 * @param getConversionRatesUseCase The use case for fetching currency rates, injected by Hilt.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConversionRatesUseCase: GetConversionRatesUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        // 1. Perform an immediate, synchronous check for the initial network state.
        if (connectivityObserver.currentStatus == ConnectivityObserver.Status.Available) {
            _uiState.update { it.copy(isOnline = true) }
            fetchRates()
        } else {
            // If offline at launch, immediately stop loading and show an error.
            _uiState.update {
                it.copy(
                    isOnline = false,
                    isLoading = false,
                    error = "You are offline. Please connect to the internet to get the latest rates."
                )
            }
        }

        // 2. Start a separate coroutine to observe future connectivity changes.
        observeConnectivityChanges()
    }


    private fun observeConnectivityChanges() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                val isOnline = status == ConnectivityObserver.Status.Available
                val wasPreviouslyOffline = !_uiState.value.isOnline

                _uiState.update { it.copy(isOnline = isOnline) }

                // If we just reconnected and still don't have data, try fetching again.
                if (isOnline && wasPreviouslyOffline && _uiState.value.conversionRates == null) {
                    fetchRates()
                }
            }
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.AmountChanged -> {
                _uiState.update { it.copy(amount = event.amount) }
                calculateResult()
            }
            is MainScreenEvent.FromCurrencyChanged -> {
                _uiState.update { it.copy(fromCurrency = event.currency) }
                fetchRates()
            }
            is MainScreenEvent.ToCurrencyChanged -> {
                _uiState.update { it.copy(toCurrency = event.currency) }
                calculateResult()
            }
            MainScreenEvent.SwapCurrencies -> {
                val currentState = _uiState.value
                _uiState.update {
                    it.copy(
                        fromCurrency = currentState.toCurrency,
                        toCurrency = currentState.fromCurrency
                    )
                }
                fetchRates()
            }
            MainScreenEvent.Retry -> fetchRates()
        }
    }

    private fun fetchRates() {
        if (!_uiState.value.isOnline) {
            _uiState.update { it.copy(isLoading = false, error = "Cannot fetch rates. You are offline.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getConversionRatesUseCase(_uiState.value.fromCurrency)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            conversionRates = result.data
                        )
                    }
                    calculateResult()
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message,
                            conversionRates = null
                        )
                    }
                }
            }
        }
    }

    private fun calculateResult() {
        val state = _uiState.value
        val amountValue = state.amount.toDoubleOrNull()
        val rates = state.conversionRates?.rates

        if (amountValue == null) {
            _uiState.update { it.copy(conversionResult = "Invalid Amount") }
            return
        }
        if (rates == null) {
            _uiState.update { it.copy(conversionResult = null) }
            return
        }
        val conversionRate = rates[state.toCurrency]
        if (conversionRate == null) {
            _uiState.update { it.copy(conversionResult = null, error = "Rate for ${state.toCurrency} not available.") }
            return
        }
        val result = amountValue * conversionRate
        _uiState.update { it.copy(conversionResult = "%.2f".format(result)) }
    }
}
