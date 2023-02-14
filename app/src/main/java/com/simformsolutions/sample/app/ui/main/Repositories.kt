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

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.simformsolutions.sample.app.ui.theme.shapes
import com.simformsolutions.sample.app.ui.theme.topAppBarTitleColor
import com.simformsolutions.sample.app.utils.GITHUB_UPDATED_AT_TIMESTAMP
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Repositories(
    repositoryData: Flow<PagingData<RepositoriesQuery.Node>>
) {
    val repositories = repositoryData.collectAsLazyPagingItems()
    val shouldShowCircularProgressBar by remember {
        derivedStateOf { repositories.itemCount == 0 }
    }

    Scaffold(
        topBar = { TopAppBar() }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                isHidden = !shouldShowCircularProgressBar
            )
            RepositoriesList(
                modifier = Modifier.fillMaxSize(),
                repositories = repositories
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.simform_repo_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = topAppBarTitleColor
            )
        },
        modifier = Modifier.shadow(7.dp)
    )
}

@Composable
private fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    isHidden: Boolean = true
) {
    CircularProgressIndicator(
        modifier = modifier
            .alpha(if (isHidden) 0f else 1f)
    )
}

@Composable
private fun RepositoriesList(
    modifier: Modifier = Modifier,
    repositories: LazyPagingItems<RepositoriesQuery.Node>
) {
    val uriHandler = LocalUriHandler.current
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        items(repositories) {
            it ?: return@items
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
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
                        ) { uriHandler.openUri(it.url.toString()) }
                ) {
                    NameAndLanguage(it)
                    it.description?.let { description -> Description(description) }
                    LastUpdateAndStars(
                        it.updatedAt.toString(),
                        it.stargazerCount.toString()
                    )
                }
            }
        }
    }
}

@Composable
private fun NameAndLanguage(data: RepositoriesQuery.Node) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = data.name,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        data.languages?.edges
            ?.sortedByDescending { it?.size }
            ?.firstOrNull()
            ?.node
            ?.let { language ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .shadow(1.dp, shapes.large)
                        .clip(shapes.large)
                        .background(MaterialTheme.colorScheme.background)
                        .border(0.5.dp, MaterialTheme.colorScheme.onSurface, shapes.large)
                        .padding(5.dp)
                        .padding(horizontal = 7.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                Color(
                                    android.graphics.Color.parseColor(
                                        language.color ?: "#FFFFFF"
                                    )
                                )
                            )
                    )
                    Text(
                        text = language.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                }
            }
    }
}

@Composable
fun Description(description: String) {
    Text(
        text = description,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun LastUpdateAndStars(
    lastUpdatedTimeStamp: String,
    starCount: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
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
                    fontSize = 12.sp,
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
                fontSize = 14.sp
            )
        }
    }
}