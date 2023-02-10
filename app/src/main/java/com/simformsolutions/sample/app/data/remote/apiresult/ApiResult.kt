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
package com.simformsolutions.sample.app.data.remote.apiresult

import com.simformsolutions.sample.app.data.remote.ApiService

/**
 * Interface to wrap API results from a Retrofit API call
 * Use this class as the return type for every function placed in [ApiService]
 *
 * Usage:
 * ```
 *     /* Function getUserData returns an instance of ApiResult */
 *     apiRepository.getUserData(id = 101)
 *         .onSuccess {
 *             // Called when api call succeeds
 *         }
 *         .onError {
 *             // Called when error occurs while making an API call
 *         }
 *         .onException {
 *             // Called when exception is generated while making an API call
 *         }
 * ```
 */
interface ApiResult<T: Any>

/**
 * Denotes API success
 */
data class ApiSuccess<T: Any>(val data: T) : ApiResult<T>

/**
 * Denotes error while making an API call
 */
data class ApiError<T: Any>(val code: Int, val message: String?) : ApiResult<T>

/**
 * Denotes exception generated while making an API call
 */
data class ApiException<T: Any>(val exception: Throwable) : ApiResult<T>
