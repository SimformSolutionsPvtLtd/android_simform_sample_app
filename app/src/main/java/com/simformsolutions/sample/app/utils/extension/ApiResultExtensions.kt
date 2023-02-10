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

import com.simformsolutions.sample.app.data.remote.apiresult.ApiError
import com.simformsolutions.sample.app.data.remote.apiresult.ApiException
import com.simformsolutions.sample.app.data.remote.apiresult.ApiResult
import com.simformsolutions.sample.app.data.remote.apiresult.ApiSuccess

/**
 * Execute [executable] if the [ApiResult] is of type [ApiResult.ApiSuccess]
 *
 * @param executable    Block to execute on [ApiResult.ApiSuccess]
 *
 * @return [ApiResult] instance
 */
suspend fun <T : Any> ApiResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): ApiResult<T> = apply {
    if (this is ApiSuccess<T>) {
        executable(data)
    }
}

/**
 * Execute [executable] if the [ApiResult] is of type [ApiResult.ApiError]
 *
 * @param executable    Block to execute on [ApiResult.ApiError]
 *
 * @return [ApiResult] instance
 */
suspend fun <T : Any> ApiResult<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit
): ApiResult<T> = apply {
    if (this is ApiError<T>) {
        executable(code, message)
    }
}

/**
 * Execute [executable] if the [ApiResult] is of type [ApiResult.ApiException]
 *
 * @param executable    Block to execute on [ApiResult.ApiException]
 *
 * @return [ApiResult] instance
 */
suspend fun <T : Any> ApiResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit
): ApiResult<T> = apply {
    if (this is ApiException<T>) {
        executable(exception)
    }
}
