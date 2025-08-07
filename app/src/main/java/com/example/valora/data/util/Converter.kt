package com.example.valora.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromRatesMap(rates: Map<String, Double>): String {
        return gson.toJson(rates)
    }

    @TypeConverter
    fun toRatesMap(json: String): Map<String, Double> {
        val type = object : TypeToken<Map<String, Double>>() {}.type
        return gson.fromJson(json, type)
    }
}
