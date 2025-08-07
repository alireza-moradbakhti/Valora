package com.example.valora.domain.model

/**
 * A clean data model representing the conversion rates for a base currency.
 * This model is used by the domain and presentation layers, making them independent
 * of the data layer's specific DTOs.
 */
data class ConversionRates(
    val baseCode: String,
    val rates: Map<String, Double>
)
