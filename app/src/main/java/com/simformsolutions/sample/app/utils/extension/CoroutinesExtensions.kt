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

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.CancellableContinuation

/**
 * Execute this function only if [CancellableContinuation.isActive] is true.
 * @param exec function to be executed
 */
fun <T> CancellableContinuation<T>.ifActive(exec: CancellableContinuation<T>.() -> Unit) {
    if (isActive) exec()
}

/**
 * Execute this function only if [CancellableContinuation.isCancelled] is true.
 * @param exec function to be executed
 */
fun <T> CancellableContinuation<T>.ifCancelled(exec: CancellableContinuation<T>.() -> Unit) {
    if (isCancelled) exec()
}

/**
 * Execute this function only if [CancellableContinuation.isCancelled] is true.
 * @param exec function to be executed
 */
fun <T> CancellableContinuation<T>.ifCompleted(exec: CancellableContinuation<T>.() -> Unit) {
    if (isCompleted) exec()
}

/**
 * Resume with [value] only when [CancellableContinuation.isActive] is true.
 * @param value value to be passed in resume
 */
fun <T> CancellableContinuation<T>.resumeIfActive(value: T) {
    ifActive {
        resume(value)
    }
}

/**
 * Resume with exception only when [CancellableContinuation.isActive] is true.
 * @param exception throwable to be passed in resume with exception
 */
fun <T> CancellableContinuation<T>.resumeWithExceptionIfActive(exception: Throwable) {
    ifActive {
        resumeWithException(exception)
    }
}
