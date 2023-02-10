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

import com.google.gson.Gson

/**
 * Converts JSON String to POJO.
 */
inline fun <reified T> String.toPojo(): T {
    return Gson().fromJson(this, T::class.java)
}

/**
 * Converts any object to JSON string.
 */
inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}
