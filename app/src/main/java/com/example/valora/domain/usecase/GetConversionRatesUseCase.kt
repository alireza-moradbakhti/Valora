package com.example.valora.domain.usecase

import com.example.valora.domain.model.ConversionRates
import com.example.valora.domain.repository.CurrencyRepository
import com.example.valora.util.Resource
import javax.inject.Inject


/**
 * Use case for getting the currency conversion rates.
 * A use case encapsulates a single, specific business rule of the application.
 * This class will be injected into the ViewModel.
 */
class GetConversionRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    /**
     * The invoke operator allows us to call an instance of this class as if it were a function.
     * e.g., getConversionRatesUseCase("USD")
     */
    suspend operator fun invoke(baseCurrency: String): Resource<ConversionRates> {
        return repository.getRates(baseCurrency)
    }
}