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

package com.simformsolutions.sample.app.utils

import androidx.lifecycle.LiveData
import com.simformsolutions.sample.app.data.repository.FlavorRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * App flavor delegate.
 *
 * Usage:
 *  In ViewModel:
 *      class YourViewModel @Inject constructor(
 *          flavorDelegate: FlavorDelegate
 *      ): BaseViewModel(), FlavorDelegate by flavorDelegate
 *
 *  In XML:
 *      <TextView
 *          ...
 *          app:showFlavorInfo="@{viewModel.flavor}"
 *          app:flavorDelegate="@{viewModel}"
 *          ...
 *          />
 */
interface FlavorDelegate {
    /**
     * [ProductFlavor.Flavor] [LiveData]
     */
    val flavor: LiveData<ProductFlavor.Flavor>

    /**
     * Set [ProductFlavor.Flavor]
     */
    fun setFlavor(newFlavor: ProductFlavor.Flavor)
}

@Singleton
class FlavorDelegateImpl @Inject constructor(private val flavorRepository: FlavorRepository) : FlavorDelegate {
    override val flavor: LiveData<ProductFlavor.Flavor>
        get() = flavorRepository.getFlavorLive()

    override fun setFlavor(newFlavor: ProductFlavor.Flavor) {
        flavorRepository.setFlavor(newFlavor)
    }
}