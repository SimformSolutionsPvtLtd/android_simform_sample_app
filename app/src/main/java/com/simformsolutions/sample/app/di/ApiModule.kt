/*
* Copyright 2023 Simform
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.simformsolutions.sample.app.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.simformsolutions.sample.app.BuildConfig
import com.simformsolutions.sample.app.data.remote.ApiService
import com.simformsolutions.sample.app.data.remote.apiresult.ApiResultCallAdapterFactory
import com.simformsolutions.sample.app.utils.Urls
import com.simformsolutions.sample.app.utils.pref.FlavorPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val HTTP_LOGGING_INTERCEPTOR: String = "HTTP_LOGGING_INTERCEPTOR"
private const val OKHTTP_CLIENT = "OKHTTP_CLIENT"
private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB
private const val NETWORK_CALL_TIMEOUT: Long = 1 // Minute

/**
 * Provides remote APIs dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    @Named(HTTP_LOGGING_INTERCEPTOR)
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Named(OKHTTP_CLIENT)
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        @Named(HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, CACHE_SIZE))
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.MINUTES)
        .writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.MINUTES)
        .build()

    @Provides
    fun provideRetrofit(
        @Named(OKHTTP_CLIENT) okHttpClient: OkHttpClient,
        flavorPreferences: FlavorPreferences,
        apiResultCallAdapterFactory: ApiResultCallAdapterFactory
    ): ApiService = Retrofit.Builder()
        .baseUrl(
            Urls.getBaseUrl(flavorPreferences.flavor)
                .also { Timber.d("Setting Base URL : $it") }
        )
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(apiResultCallAdapterFactory)
        .build()
        .create(ApiService::class.java)

    @Provides
    fun provideApolloClient(
        flavorPreferences: FlavorPreferences,
    ): ApolloClient = ApolloClient.Builder()
        .serverUrl(Urls.getBaseUrl(flavorPreferences.flavor))
        .addHttpHeader("Authorization", "bearer ${BuildConfig.GITHUB_TOKEN}")
        .addHttpHeader("Accept", "application/vnd.github.v4.idl")
        .build()
}
