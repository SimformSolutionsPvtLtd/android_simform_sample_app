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

package com.simformsolutions.sample.app.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.simformsolutions.sample.app.RepositoriesQuery
import com.simformsolutions.sample.app.type.OrderDirection
import com.simformsolutions.sample.app.type.RepositoryOrder
import com.simformsolutions.sample.app.type.RepositoryOrderField
import com.simformsolutions.sample.app.utils.exception.ApolloException

/**
 * The paging source for loading Simform organisation repositories.
 */
class SimformRepositoriesSource private constructor(
    private val apolloClient: ApolloClient
) : PagingSource<SimformRepositoriesSource.PagingKey, RepositoriesQuery.Node>() {

    override fun getRefreshKey(state: PagingState<PagingKey, RepositoriesQuery.Node>): PagingKey? =
        null

    override suspend fun load(params: LoadParams<PagingKey>): LoadResult<PagingKey, RepositoriesQuery.Node> =
        try {
            val response = getRepositories(params.key?.before, params.key?.after)
            if (response.hasErrors()) {
                LoadResult.Error(ApolloException(response.errors))
            } else {
                response.data?.organization?.repositories?.let {
                    LoadResult.Page(
                        data = it.nodes?.filterNotNull().orEmpty(),
                        prevKey =
                            if (it.pageInfo.hasPreviousPage)
                                PagingKey(before = it.pageInfo.startCursor)
                            else null,
                        nextKey =
                            if (it.pageInfo.hasNextPage)
                                PagingKey(after = it.pageInfo.endCursor)
                            else null
                    )
                } ?: LoadResult.Invalid()
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    /**
     * Get repositories.
     *
     * @param before The before cursor
     * @param after The after cursor
     */
    private suspend fun getRepositories(before: String?, after: String?): ApolloResponse<RepositoriesQuery.Data> =
        apolloClient.query(
            RepositoriesQuery(
                login = SIMFORM_ORG,
                before = Optional.present(before),
                after = Optional.present(after),
                first = Optional.present(PAGE_LENGTH),
                languagesFirst = Optional.present(PAGE_LENGTH_LANGUAGES),
                orderBy = Optional.present(
                    RepositoryOrder(
                        field = RepositoryOrderField.STARGAZERS,
                        direction = OrderDirection.DESC
                    )
                )
            )
        ).execute()

    /**
     * The paging key
     *
     * @param before The before cursor
     * @param after The after cursor
     */
    data class PagingKey(
        val before: String? = null,
        val after: String? = null
    )

    companion object {
        const val PAGE_LENGTH = 5

        private const val SIMFORM_ORG = "SimformSolutionsPvtLtd"
        private const val PAGE_LENGTH_LANGUAGES = 5

        /**
         * Provides the instance of [SimformRepositoriesSource]
         *
         * @param apolloClient The [ApolloClient] instance
         *
         * @return An instance of [SimformRepositoriesSource]
         */
        fun getInstance(apolloClient: ApolloClient): SimformRepositoriesSource =
            SimformRepositoriesSource(apolloClient)
    }
}