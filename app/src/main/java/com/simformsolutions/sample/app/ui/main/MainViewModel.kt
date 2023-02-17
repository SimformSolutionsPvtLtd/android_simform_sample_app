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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.simformsolutions.sample.app.data.remote.paging.SimformRepositoriesSource
import com.simformsolutions.sample.app.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * [ViewModel] for [MainActivity]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val repositories = Pager(
        PagingConfig(pageSize = SimformRepositoriesSource.PAGE_LENGTH)
    ) {
        mainRepository.getSimformRepositoriesSource()
    }.flow.cachedIn(viewModelScope)

    fun setIsRefreshing(isRefreshing: Boolean) {
        _isRefreshing.value = isRefreshing
    }

    companion object {
        private val TAG = MainViewModel::class.java.canonicalName
    }
}