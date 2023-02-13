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

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * Sets bottom border
 *
 * @param strokeWidth Stroke width
 * @param color Color of the stroke
 */
fun Modifier.setBottomBorder(
    strokeWidth: Float = 1f,
    color: Color = Color.LightGray
) = then(
    drawBehind {
        val width = strokeWidth * density
        val y = size.height - width / 2
        drawLine(
            color,
            Offset(0f, y),
            Offset(size.width, y),
            width
        )
    }
)