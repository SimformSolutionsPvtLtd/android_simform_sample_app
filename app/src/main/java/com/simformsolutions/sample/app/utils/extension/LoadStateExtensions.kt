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
package com.simformsolutions.sample.app.utils.extension

import androidx.paging.LoadState

/**
 * Set load state listener for the current [LoadState]
 *
 * @param onError       Called when error has occurred
 * @param onLoading     Called when list is loading
 * @param onNotLoading  Called when list is being scrolled but not loading
 */
fun LoadState.setLoadStateListener(
    onError: (Throwable) -> Unit = { },
    onLoading: () -> Unit = { },
    onNotLoading: (Boolean) -> Unit = { }
) = when (this) {
    is LoadState.Error -> onError(error)
    is LoadState.Loading -> onLoading()
    is LoadState.NotLoading -> onNotLoading(endOfPaginationReached)
}