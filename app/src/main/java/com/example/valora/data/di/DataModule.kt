package com.example.valora.data.di

import android.content.Context
import androidx.room.Room
import com.example.valora.data.local.CurrencyDao
import com.example.valora.data.local.ValoraDatabase
import com.example.valora.data.remote.ExchangeRateApi
import com.example.valora.util.AppConstants
import com.example.valora.util.ConnectivityObserver
import com.example.valora.util.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt module for providing dependencies for the Data layer.
 * This includes Retrofit, OkHttpClient, and the API service interface.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    /**
     * Provides a singleton instance of OkHttpClient.
     * Includes a logging interceptor to view network requests and responses in Logcat,
     * which is extremely useful for debugging.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    /**
     * Provides a singleton instance of Retrofit.
     * Configured with the base URL, a GSON converter for JSON serialization/deserialization,
     * and the custom OkHttpClient.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return AppConstants.BASE_URL
    }

    @Provides
    @Singleton
    fun provideValoraDatabase(@ApplicationContext context: Context): ValoraDatabase {
        return Room.databaseBuilder(
            context,
            ValoraDatabase::class.java,
            ValoraDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: ValoraDatabase): CurrencyDao {
        return database.currencyDao()
    }

    /**
     * Provides a singleton instance of our [ExchangeRateApi] interface.
     * Retrofit uses this to generate the implementation for making API calls.
     */
    @Provides
    @Singleton
    fun provideExchangeRateApi(retrofit: Retrofit): ExchangeRateApi {
        return retrofit.create(ExchangeRateApi::class.java)
    }

    // Add this provider for our new ConnectivityObserver
    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}