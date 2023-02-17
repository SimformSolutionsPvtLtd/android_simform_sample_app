/*
 *  Copyright 2023 Simform
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

object Libs {
    // UI
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLER_VIEW}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    // Compose
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE_MATERIAL}"
    const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:${Versions.COMPOSE_MATERIAL_3}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:${Versions.COMPOSE_UI_TOOLING}"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:${Versions.COMPOSE_UI_TOOLING_PREVIEW}"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    const val VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.VIEWMODEL_COMPOSE}"
    const val RUNTIME_LIVEDATA_COMPOSE = "androidx.compose.runtime:${Versions.RUNTIME_LIVE_DATA}"
    const val COMPOSE_UI_TEST_JUNIT_4 = "androidx.compose.ui:${Versions.COMPOSE_UI_TEST_JUNIT_4}"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:${Versions.COMPOSE_UI_TEST_MANIFEST}"
    const val COMPOSE_PAGINATION = "androidx.paging:paging-compose:${Versions.COMPOSE_PAGINATION}"

    // Jetpack
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_DAGGER_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.HILT_JETPACK}"
    const val PAGINATION = "androidx.paging:paging-runtime:${Versions.PAGINATION}"

    // Testing
    const val ARCH_CORE_TESTING = "androidx.arch.core:core-testing:${Versions.ARCH_CORE_TESTING}"
    const val TEST_RUNNER = "androidx.test:runner:${Versions.TEST_RUNNER}"
    const val TEST_RULES = "androidx.test:rules:${Versions.TEST_RULES}"
    // Espresso
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO_CORE}"
    // Hamcrest
    const val HAMCREST = "org.hamcrest:hamcrest-library:${Versions.HAMCREST}"
    // Mockito
    const val MOCKITO_CORE = "org.mockito:mockito-core:${Versions.MOCKITO_CORE}"
    const val MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
    // JUnit
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"
    // Coroutines
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"

    // Coroutines
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    // Retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP3 = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP3}"
    const val OKHTTP3_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}"

    // AppCenter
    const val APPCENTER_ANALYTICS = "com.microsoft.appcenter:appcenter-analytics:${Versions.APPCENTER}"
    const val APPCENTER_CRASHES = "com.microsoft.appcenter:appcenter-crashes:${Versions.APPCENTER}"

    // Timber
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    // Gson
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"

    // Kotlin Reflect
    const val REFLECT = "reflect"

    // Alerter
    const val ALERTER = "com.github.tapadoo:alerter:${Versions.ALERTER}"

    // Loading Button
    const val LOADING_BUTTON = "br.com.simplepass:loading-button-android:${Versions.LOADING_BUTTON}"

    // Shimmer
    const val SHIMMER = "com.facebook.shimmer:shimmer:${Versions.SHIMMER}"

    // GLIDE
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"

    // Apollo GraphQL
    const val APOLLO_GRAPH_QL = "com.apollographql.apollo3:apollo-runtime:${Versions.APOLLO_GRAPH_QL}"

    // System UI Controller
    const val SYSTEM_UI_CONTROLLER = "com.google.accompanist:accompanist-systemuicontroller:${Versions.SYSTEM_UI_CONTROLLER}"
}
