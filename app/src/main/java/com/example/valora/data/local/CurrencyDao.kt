package com.example.valora.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    /**
     * Inserts or replaces conversion rates.
     * This function does not need to be a suspend function, as Room handles
     * the background threading for simple insert/update/delete operations automatically.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rates: ConversionRatesEntity)

    /**
     * Gets the rates for a given currency as a Flow.
     * Using Flow allows the UI to automatically update whenever the cached data changes.
     * Room handles the background threading for Flow-based queries.
     */
    @Query("SELECT * FROM conversion_rates WHERE baseCode = :baseCode")
    fun getRates(baseCode: String): Flow<ConversionRatesEntity?>

}