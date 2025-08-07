package com.example.valora.domain.di

import com.example.valora.data.repository.CurrencyRepositoryImpl
import com.example.valora.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Hilt module for providing dependencies for the Domain layer.
 * This module is installed in the ViewModelComponent, meaning its bindings are scoped
 * to the lifecycle of a ViewModel.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    /**
     * Binds the [CurrencyRepositoryImpl] to the [CurrencyRepository] interface.
     * When a ViewModel requests a [CurrencyRepository], Hilt will provide an
     * instance of [CurrencyRepositoryImpl]. Using @Binds is more efficient than @Provides
     * for simple interface-to-implementation bindings.
     *
     * @param currencyRepositoryImpl The implementation of the repository.
     * @return An instance that conforms to the [CurrencyRepository] interface.
     */
    @Binds
    @ViewModelScoped
    abstract fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}