package com.simformsolutions.sample.app.di

import com.simformsolutions.sample.app.data.repository.FlavorRepository
import com.simformsolutions.sample.app.data.repository.FlavorRepositoryImpl
import com.simformsolutions.sample.app.utils.FlavorDelegate
import com.simformsolutions.sample.app.utils.FlavorDelegateImpl
import com.simformsolutions.sample.app.utils.pref.FlavorPreferences
import com.simformsolutions.sample.app.utils.pref.FlavorPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Defines all the classes that need to be provided in the scope of the app.
 * If they are singleton mark them as '@Singleton'.
 *
 * NOTE : This module should only be used for app's flavor.
 */
@Module
@InstallIn(SingletonComponent::class)
object FlavorModule {

    @Singleton
    @Provides
    fun provideFlavorPreferences(flavorPreferencesImpl: FlavorPreferencesImpl): FlavorPreferences = flavorPreferencesImpl

    @Singleton
    @Provides
    fun provideFlavorRepository(flavorRepositoryImpl: FlavorRepositoryImpl): FlavorRepository = flavorRepositoryImpl

    @Singleton
    @Provides
    fun provideFlavorDelegate(flavorDelegateImpl: FlavorDelegateImpl): FlavorDelegate = flavorDelegateImpl
}