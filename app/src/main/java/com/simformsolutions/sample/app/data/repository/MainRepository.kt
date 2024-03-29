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

package com.simformsolutions.sample.app.data.repository

import com.apollographql.apollo3.ApolloClient
import com.simformsolutions.sample.app.data.remote.paging.SimformRepositoriesSource
import javax.inject.Inject

/**
 * Main repository.
 */
interface MainRepository {
    /**
     * Get the instance of [SimformRepositoriesSource]
     *
     * @return The instance of [SimformRepositoriesSource]
     */
    fun getSimformRepositoriesSource(): SimformRepositoriesSource
}

/**
 * Implementation of [MainRepository]
 *
 * @param apolloClient The [ApolloClient] instance
 */
class MainRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : MainRepository {

    override fun getSimformRepositoriesSource(): SimformRepositoriesSource =
        SimformRepositoriesSource.getInstance(apolloClient)
}
