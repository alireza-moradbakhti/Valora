package com.example.valora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.valora.data.util.Converters
import com.example.valora.util.AppConstants

@Database(
    entities = [ConversionRatesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ValoraDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        const val DATABASE_NAME = AppConstants.APP_DATABASE_NAME
    }
}