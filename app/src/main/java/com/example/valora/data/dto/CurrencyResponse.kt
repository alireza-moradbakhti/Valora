package com.example.valora.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for the API response.
 * The structure of this class matches the JSON response from the ExchangeRate-API.
 * Using @SerializedName allows us to use different variable names in our Kotlin code
 * than the key names in the JSON response.
 */
data class CurrencyResponse(
    @SerializedName("result")
    val result: String,

    @SerializedName("base_code")
    val baseCode: String,

    @SerializedName("conversion_rates")
    val conversionRates: Map<String, Double>
)
