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
package com.simformsolutions.sample.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simformsolutions.sample.app.ui.theme.shapes

/**
 * A Chip to represent a block containing text and a leading icon
 *
 * @param modifier          The [Modifier] to apply
 * @param elevation         Elevation of the chip
 * @param shape             [Shape] of the chip
 * @param backgroundColor   Background color to set
 * @param borderWidth       Width of the border
 * @param borderColor       Color of the border
 * @param leadingIcon       Leading icon to set
 * @param content           Content to set
 */
@Composable
fun Chip(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    shape: Shape = shapes.small,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Black,
    leadingIcon: @Composable () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(elevation, shape)
            .clip(shape)
            .background(backgroundColor)
            .border(borderWidth, borderColor, shape)
            .padding(vertical = 5.dp, horizontal = 12.dp)
    ) {
        leadingIcon()
        content()
    }
}