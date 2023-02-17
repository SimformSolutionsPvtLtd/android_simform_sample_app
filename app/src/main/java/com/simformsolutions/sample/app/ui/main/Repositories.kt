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

package com.simformsolutions.sample.app.ui.main

import android.content.Context
import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.simformsolutions.sample.app.R
import com.simformsolutions.sample.app.RepositoriesQuery
import com.simformsolutions.sample.app.ui.components.Chip
import com.simformsolutions.sample.app.ui.theme.shapes
import com.simformsolutions.sample.app.ui.theme.simformRepositoriesAppBarTitleColor
import com.simformsolutions.sample.app.ui.theme.typography
import com.simformsolutions.sample.app.utils.GITHUB_UPDATED_AT_TIMESTAMP
import com.simformsolutions.sample.app.utils.extension.setLoadStateListener
import com.simformsolutions.sample.app.utils.extension.toColor
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Repositories(
    repositoryData: Flow<PagingData<RepositoriesQuery.Node>>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = { },
    onListLoaded: () -> Unit = { }
) {
    var shouldShowInitialLoader by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }
    val repositories = repositoryData.collectAsLazyPagingItems()
    repositories.loadState.refresh.setLoadStateListener(
        onLoading = {
            showErrorMessage = false
            shouldShowInitialLoader = !isRefreshing
        },
        onNotLoading = {
            onListLoaded()
            showErrorMessage = false
            shouldShowInitialLoader = false
        },
        onError = {
            onListLoaded()
            showErrorMessage = true
            shouldShowInitialLoader = false
        }
    )
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            onRefresh()
            repositories.refresh()
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (showErrorMessage) {
            ErrorMessage(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(simformRepositoriesAppBarTitleColor),
            )
        }
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(if (shouldShowInitialLoader) 1f else 0f)
        )
        RepositoriesList(
            modifier = Modifier.fillMaxSize(),
            repositories = repositories
        )
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.oops_label),
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.on_screen_error_message),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun RepositoriesList(
    modifier: Modifier = Modifier,
    repositories: LazyPagingItems<RepositoriesQuery.Node>
) {
    val context = LocalContext.current
    var shouldShowRepositoryPageLoader by remember { mutableStateOf(false) }
    repositories.loadState.append.setLoadStateListener(
        onLoading = { shouldShowRepositoryPageLoader = true },
        onNotLoading = { shouldShowRepositoryPageLoader = false },
        onError = { showErrorToast(context, it.message.toString()) }
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        items(repositories) {
            it ?: return@items
            RepositoryCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                repository = it
            )
        }
        if (shouldShowRepositoryPageLoader && repositories.itemSnapshotList.isNotEmpty()) {
            item {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
private fun RepositoryCard(
    modifier: Modifier = Modifier,
    repository: RepositoriesQuery.Node
) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = modifier,
        shape = shapes.large,
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { uriHandler.openUri(repository.url.toString()) }
        ) {
            NameAndLanguage(
                modifier = Modifier.fillMaxWidth(),
                repository = repository
            )
            repository.description?.let { description ->
                Description(description = description)
            }
            LastUpdateAndStars(
                modifier = Modifier.fillMaxWidth(),
                lastUpdatedTimeStamp = repository.updatedAt.toString(),
                starCount = repository.stargazerCount.toString()
            )
        }
    }
}

@Composable
private fun NameAndLanguage(
    modifier: Modifier = Modifier,
    repository: RepositoriesQuery.Node
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = repository.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = typography.headlineLarge,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        repository.languages?.edges
            ?.sortedByDescending { it?.size }
            ?.firstOrNull()
            ?.node
            ?.let { language ->
                val color = language.color?.toColor() ?: Color.White
                Chip(
                    elevation = 1.dp,
                    shape = shapes.large,
                    borderWidth = 0.5.dp,
                    borderColor = MaterialTheme.colorScheme.onSurface,
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(shapes.extraSmall)
                                .background(color)
                        )
                    },
                    content = {
                        Text(
                            text = language.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = typography.bodyMedium
                        )
                    }
                )
            }
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    description: String
) {
    Text(
        text = description,
        style = typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
private fun LastUpdateAndStars(
    modifier: Modifier = Modifier,
    lastUpdatedTimeStamp: String,
    starCount: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        SimpleDateFormat(GITHUB_UPDATED_AT_TIMESTAMP, Locale.getDefault()).let {
            it.timeZone = TimeZone.getTimeZone("GMT")
            it.parse(lastUpdatedTimeStamp)?.time?.let { time ->
                val now = System.currentTimeMillis()
                val lastUpdatedFormatted = DateUtils.getRelativeTimeSpanString(
                    time,
                    now,
                    DateUtils.MINUTE_IN_MILLIS
                )
                Text(
                    text = stringResource(R.string.last_updated_at, lastUpdatedFormatted),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = typography.bodySmall,
                    modifier = Modifier.alpha(0.7f)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = "",
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = starCount,
                color = MaterialTheme.colorScheme.onSurface,
                style = typography.bodyMedium
            )
        }
    }
}

/**
 * Shows an toast to denote an error occurring while loading repositories
 *
 * @param context   The [Context]
 * @param message   The error message to toast
 */
private fun showErrorToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}