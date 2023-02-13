package com.simformsolutions.sample.app.ui.main

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.simformsolutions.sample.app.R
import com.simformsolutions.sample.app.RepositoriesQuery
import kotlinx.coroutines.flow.Flow
import java.util.*

@Composable
fun Repositories(
    repositoryData: Flow<PagingData<RepositoriesQuery.Node>>
) {
    val context = LocalContext.current
    val repositories = repositoryData.collectAsLazyPagingItems()
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(repositories) {
                it ?: return@items
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 1f * density
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                Color.LightGray,
                                Offset(0f, y),
                                Offset(size.width, y),
                                strokeWidth
                            )
                        }
                        .padding(10.dp)
                ) {
                    /**
                     * Title
                     */
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = it.name,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "View Code",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    Toast.makeText(
                                        context,
                                        "${it.name} Clicked",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        )
                    }

                    /**
                     * Repo details
                     */
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            /**
                             * Language
                             */
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color(Random().nextInt()))
                                )
                                Text(
                                    text = it.languages?.nodes?.firstOrNull()?.name.orEmpty(),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp
                                )
                            }

                            /**
                             * License
                             */
                            it.licenseInfo?.let { license ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Image(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "",
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Text(
                                        text = license.name,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            /**
                             * Forks
                             */
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.spacedBy(5.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(R.drawable.ic_moon),
//                                    contentDescription = "",
//                                    modifier = Modifier.size(15.dp)
//                                )
//                                Text(
//                                    text = it.forks.toString(),
//                                    color = Color.White,
//                                    fontSize = 12.sp
//                                )
//                            }

                            /**
                             * Stars
                             */
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Image(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "",
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = it.stargazerCount.toString(),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp
                                )
                            }

                            /**
                             * Watch count
                             */
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.spacedBy(5.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(R.drawable.ic_moon),
//                                    contentDescription = "",
//                                    modifier = Modifier.size(15.dp)
//                                )
//                                Text(
//                                    text = it.watchCount.toString(),
//                                    color = Color.White,
//                                    fontSize = 12.sp
//                                )
//                            }

                            /**
                             * Pull requests
                             */
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.spacedBy(5.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(R.drawable.ic_moon),
//                                    contentDescription = "",
//                                    modifier = Modifier.size(15.dp)
//                                )
//                                Text(
//                                    text = it.pullRequests.toString(),
//                                    color = Color.White,
//                                    fontSize = 12.sp
//                                )
//                            }
                        }

                        /**
                         * Last update
                         */
//                        Text(
//                            text = it.lastUpdate,
//                            color = Color.White,
//                            fontSize = 10.sp
//                        )
                    }
                }
            }
        }
    }
}