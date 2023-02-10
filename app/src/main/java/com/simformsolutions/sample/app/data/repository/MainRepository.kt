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

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.simformsolutions.sample.app.RepositoriesQuery
import com.simformsolutions.sample.app.di.IoDispatcher
import com.simformsolutions.sample.app.type.OrderDirection
import com.simformsolutions.sample.app.type.RepositoryOrder
import com.simformsolutions.sample.app.type.RepositoryOrderField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Main repository.
 */
interface MainRepository {
    /**
     * Get all the repositories of Simform organisation
     *
     * @return [ApolloCall] containing [RepositoriesQuery.Data]
     */
    suspend fun getSimformRepositories(): ApolloCall<RepositoriesQuery.Data>
}

/**
 * Implementation of [MainRepository]
 *
 * @param apolloClient  The instance of [ApolloClient]
 */
class MainRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): MainRepository {

    override suspend fun getSimformRepositories(): ApolloCall<RepositoriesQuery.Data> =
        withContext(ioDispatcher) {
            apolloClient.query(
                RepositoriesQuery(
                    login = SIMFORM_ORG,
                    first = Optional.present(PAGE_LENGTH),
                    languagesFirst = Optional.present(PAGE_LENGTH_LANGUAGES),
                    orderBy = Optional.present(
                        RepositoryOrder(
                            field = RepositoryOrderField.STARGAZERS,
                            direction = OrderDirection.DESC
                        )
                    )
                )
            )
        }

    companion object {
        private const val SIMFORM_ORG = "SimformSolutionsPvtLtd"
        private const val PAGE_LENGTH = 100
        private const val PAGE_LENGTH_LANGUAGES = 1
    }
}
