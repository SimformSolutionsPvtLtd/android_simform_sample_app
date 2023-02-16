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

import androidx.compose.ui.graphics.Color

/**
 * String extension to convert given string(hex code) into [Color] if possible
 *
 * @return [Color] of the given string representing hex code of the color
 *         null if exception occurred while parsing color
 */
fun String.toColor(): Color? = try {
    Color(android.graphics.Color.parseColor(this))
} catch (_: java.lang.IllegalArgumentException) {
    null
}