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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.simformsolutions.sample.app.R
import com.simformsolutions.sample.app.RepositoriesQuery
import com.simformsolutions.sample.app.utils.GITHUB_UPDATED_AT_TIMESTAMP
import com.simformsolutions.sample.app.utils.extension.setBottomBorder
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun Repositories(
    repositoryData: Flow<PagingData<RepositoriesQuery.Node>>
) {
    val repositories = repositoryData.collectAsLazyPagingItems()
    val uriHandler = LocalUriHandler.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenTitle()
        LazyColumn {
            items(repositories) {
                it ?: return@items
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .setBottomBorder()
                        .padding(10.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            uriHandler.openUri(it.url.toString())
                        }
                ) {
                    NameAndLanguage(it)
                    it.description?.let { description -> Description(description) }
                    LastUpdateAndStars(it.updatedAt.toString(), it.stargazerCount.toString())
                }
            }
        }
    }
}

@Composable
private fun ScreenTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_simform_logo),
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterStart)
        )
        Text(
            text = "Simform Solutions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
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
                LanguageBlock(language)
            }
    }
}

@Composable
fun LanguageBlock(language: RepositoriesQuery.Node1) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .shadow(1.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color(android.graphics.Color.parseColor(language.color ?: "#FFFFFF")))
        )
        Text(
            text = language.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp
        )
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
                    text = LocalContext.current.getString(R.string.last_updated_at, lastUpdatedFormatted),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp
                )
            }
        }
        RepoDetailBlock(
            imageVector = Icons.Filled.Star,
            text = starCount,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun RepoDetailBlock(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    fontSize: TextUnit = 12.sp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.size(15.dp)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = fontSize
        )
    }
}