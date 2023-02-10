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

import androidx.lifecycle.LiveData
import com.simformsolutions.sample.app.utils.ProductFlavor
import com.simformsolutions.sample.app.utils.pref.FlavorPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Flavor repository.
 */
interface FlavorRepository {
    /**
     * Get [ProductFlavor.Flavor].
     */
    fun getFlavor(): ProductFlavor.Flavor

    /**
     * Get [ProductFlavor.Flavor] as [LiveData].
     */
    fun getFlavorLive(): LiveData<ProductFlavor.Flavor>

    /**
     * Set [ProductFlavor.Flavor].
     */
    fun setFlavor(flavor: ProductFlavor.Flavor)
}

@Singleton
class FlavorRepositoryImpl @Inject constructor(
    private val flavorPreferences: FlavorPreferences
) : FlavorRepository {
    override fun getFlavor(): ProductFlavor.Flavor {
        return flavorPreferences.flavor
    }

    override fun getFlavorLive(): LiveData<ProductFlavor.Flavor> {
        return flavorPreferences.observableFlavor
    }

    override fun setFlavor(flavor: ProductFlavor.Flavor) {
        flavorPreferences.flavor = flavor
    }
}