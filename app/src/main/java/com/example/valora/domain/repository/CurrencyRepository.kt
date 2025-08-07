package com.example.valora.domain.repository

import com.example.valora.domain.model.ConversionRates
import com.example.valora.util.Resource

/**
 * The repository interface defines the contract for the data layer.
 * The domain layer uses this interface to access data, without knowing the
 * underlying implementation (e.g., network API, local database).
 * This abstraction is a key principle of Clean Architecture.
 */
interface CurrencyRepository {
    /**
     * Fetches the conversion rates for a given currency.
     *
     * @param baseCurrency The 3-letter code of the currency to fetch rates for.
     * @return A [Resource] wrapping the [ConversionRates]. The resource can be
     * in a Success, Error, or Loading state.
     */
    suspend fun getRates(baseCurrency: String): Resource<ConversionRates>
}