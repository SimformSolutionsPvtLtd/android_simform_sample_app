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
package com.simformsolutions.sample.app.ui.main

import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo3.ApolloClient
import com.simformsolutions.sample.app.ui.theme.SampleTheme
import com.simformsolutions.sample.app.ui.theme.sampleDarkColorScheme
import com.simformsolutions.sample.app.ui.theme.sampleLightColorScheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var apolloClient: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SampleTheme {
                SampleApp()
                checkAndUpdateStatusBarState(isSystemInDarkTheme())
            }
        }
    }

    private fun checkAndUpdateStatusBarState(isDarkModeEnabled: Boolean) = with(window) {
        statusBarColor = if (isDarkModeEnabled) {
            sampleDarkColorScheme.background
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                sampleLightColorScheme.primary
            } else {
                Color.White.also {
                    insetsController?.apply {
                        setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )
                    }
                }
            }
        }.toArgb()
    }
}

@Composable
fun SampleApp(
    viewModel: MainViewModel = viewModel()
) {
    Repositories(repositoryData = viewModel.repositories)
}

@Preview(showBackground = true)
@Composable
fun SamplePreview() {
   SampleApp()
}
