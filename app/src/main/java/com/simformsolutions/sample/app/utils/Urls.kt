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

/**
 * API URLs collection.
 */
object Urls {
    private const val BASE_DEV = "https://randomuser.me/api/"
    private const val BASE_QA = ""
    private const val BASE_PRODUCTION = ""

    /**
     * Get Base URL for [flavor].
     */
    fun getBaseUrl(flavor: ProductFlavor.Flavor): String = when(flavor) {
        ProductFlavor.Flavor.DEV -> BASE_DEV
        ProductFlavor.Flavor.QA -> BASE_QA
        ProductFlavor.Flavor.PRODUCTION -> BASE_PRODUCTION
    }
}
