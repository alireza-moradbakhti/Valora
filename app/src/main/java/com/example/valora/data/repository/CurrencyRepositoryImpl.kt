package com.example.valora.data.repository

import com.example.valora.BuildConfig
import com.example.valora.data.local.ConversionRatesEntity
import com.example.valora.data.local.CurrencyDao
import com.example.valora.data.remote.ExchangeRateApi
import com.example.valora.domain.model.ConversionRates
import com.example.valora.domain.repository.CurrencyRepository
import com.example.valora.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * This is the implementation of the CurrencyRepository interface.
 * It's responsible for fetching data from the network API and mapping it
 * to the domain model. It also handles exceptions and wraps the result
 * in a Resource object.
 *
 * @param api The Retrofit service for the currency exchange API, injected by Hilt.
 */
class CurrencyRepositoryImpl @Inject constructor(
    private val api: ExchangeRateApi,
    private val dao: CurrencyDao
) : CurrencyRepository {

    override suspend fun getRates(baseCurrency: String): Resource<ConversionRates> {
        return try {
            val response = api.getRates(BuildConfig.API_KEY, baseCurrency)
            val entity = ConversionRatesEntity(
                baseCode = response.baseCode,
                rates = response.conversionRates
            )

            // Manually switch to the IO dispatcher to perform the database write.
            // This prevents the main thread error and avoids the KSP bug.
            withContext(Dispatchers.IO) {
                dao.insertRates(entity)
            }

            val domainModel = ConversionRates(
                baseCode = response.baseCode,
                rates = response.conversionRates
            )
            Resource.Success(domainModel)

        } catch (e: Exception) {
            val cachedRates = dao.getRates(baseCurrency).firstOrNull()
            if (cachedRates != null) {
                val domainModel = ConversionRates(
                    baseCode = cachedRates.baseCode,
                    rates = cachedRates.rates
                )
                Resource.Success(domainModel)
            } else {
                val errorMessage = when (e) {
                    is IOException -> "Network error. Please check your connection."
                    else -> "An unexpected error occurred: ${e.message}"
                }
                Resource.Error(errorMessage)
            }
        }
    }
}