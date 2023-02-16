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

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.simformsolutions.sample.app.R
import com.simformsolutions.sample.app.ui.theme.simformRepositoriesAppBarTitleColor
import com.simformsolutions.sample.app.ui.theme.typography

/**
 * The default app bar of the application
 *
 * @param modifier  The [Modifier] to apply
 * @param title     Text shown on the app bar
 * @param color     Color of the text to show
 */
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.app_name),
    color: Color = simformRepositoriesAppBarTitleColor
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = color,
                style = typography.titleLarge
            )
        },
        modifier = modifier
    )
}