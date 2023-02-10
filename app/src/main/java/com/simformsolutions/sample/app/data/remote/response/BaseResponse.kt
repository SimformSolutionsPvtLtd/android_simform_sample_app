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
package com.simformsolutions.sample.app.data.remote.response

/**
 * Base API response.
 *
 * @property status `true` if success
 * @property message Error message if any
 * @property data Data
 *
 * Usage:
 *
 *  data class LoginResponse(
 *      override val data: LoginResponseData
 *  ) : BaseResponse<LoginResponseData>()
 *
 *  data class LoginResponseData(
 *      val userId: String
 *  )
 */
abstract class BaseResponse<D : Any> {
    val status: Boolean = false

    val message: String = ""

    abstract val data: D

    override fun toString(): String {
        return "Status: $status\nMessage: $message\nData: $data"
    }
}

/**
 * Executes [onSuccess] when [BaseResponse.status] is `true`.
 *
 * @param onSuccess Invoked when [BaseResponse.status] is `true`
 */
inline fun <D : Any> BaseResponse<D>.onSuccess(crossinline onSuccess: (D) -> Unit) {
    if (status) {
        onSuccess(data)
    }
}

/**
 * Executes [onSuccess] when [BaseResponse.status] is `true`.
 * Suspend function with suspend [onSuccess].
 *
 * @param onSuccess Invoked when [BaseResponse.status] is `true`
 */
suspend inline fun <D : Any> BaseResponse<D>.suspendOnSuccess(crossinline onSuccess: suspend (D) -> Unit) {
    if (status) {
        onSuccess(data)
    }
}
