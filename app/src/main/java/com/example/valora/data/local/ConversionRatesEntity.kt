package com.example.valora.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversion_rates")
data class ConversionRatesEntity(
    @PrimaryKey
    val baseCode: String,
    val rates: Map<String, Double>,
    val timestamp: Long = System.currentTimeMillis()
)
