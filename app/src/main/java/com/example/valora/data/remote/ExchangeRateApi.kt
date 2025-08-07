package com.example.valora.data.remote

import com.example.valora.data.dto.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface for the ExchangeRate-API.
 * This defines the HTTP operations.
 */
interface ExchangeRateApi {
    /**
     * Fetches the latest conversion rates for a given base currency.
     * The URL will be constructed as: BASE_URL/v6/YOUR_API_KEY/latest/{currencyCode}
     * e.g., https://v6.exchangerate-api.com/v6/YOUR_API_KEY/latest/USD
     *
     * @param apiKey Your personal API key.
     * @param currencyCode The 3-letter currency code (e.g., "USD") to get rates for.
     * @return A [CurrencyResponse] object containing the conversion rates.
     */
    @GET("v6/{apiKey}/latest/{currencyCode}")
    suspend fun getRates(
        @Path("apiKey") apiKey: String,
        @Path("currencyCode") currencyCode: String
    ): CurrencyResponse
}