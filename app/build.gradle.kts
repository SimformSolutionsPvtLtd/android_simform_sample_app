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

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.Kotlin.ANDROID)
    kotlin(Plugins.Kotlin.KAPT)
    id(Plugins.HILT)
    id(Plugins.APOLLO_GRAPH_QL).version(Versions.APOLLO_GRAPH_QL)
}

apply {
    // Quality gradle needs changes to work with Compose
//    from(rootPath(appendPath = Plugins.QUALITY))
}

android {
    compileSdk = Versions.COMPILE_SDK
    buildToolsVersion = Versions.BUILD_TOOLS

    signingConfigs {
        create("release") {
            storeFile = rootProject.file(project.property("releaseKeystoreFileName") as String)
            storePassword = project.property("releaseStorePassword") as String
            keyAlias = project.property("releaseKeyAlias") as String
            keyPassword = project.property("releaseKeyPassword") as String
        }
    }

    defaultConfig {
        applicationId = App.ID
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = App.Version.CODE
        versionName = App.Version.NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.incremental"] = "true"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Uncomment when release keystore is ready
            // signingConfig = signingConfigs.getByName("release")
        }

        buildTypes.forEach { type ->
            val appCenter = BuildConfigFields.APPCENTER_SECRET
            type.buildConfigField(appCenter.type, appCenter.title, appCenter.value)
            val githubField = BuildConfigFields.GITHUB_TOKEN
            val githubToken = gradleLocalProperties(rootDir).getProperty(githubField.title)
            type.buildConfigField(githubField.type, githubField.title, githubToken)
        }
    }


    flavorDimensions.add(App.Dimension.DEFAULT)

    productFlavors {
        create(App.Flavor.DEV) {

        }

        create(App.Flavor.QA) {

        }

        create(App.Flavor.PRODUCTION) {

        }
    }

    buildFeatures {
        dataBinding = false
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(defaultFileTree())

    // Core
    implementation(Libs.CORE_KTX)

    // UI
    implementation(Libs.APPCOMPAT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.RECYCLERVIEW)
    implementation(Libs.PAGINATION)

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2022.12.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    // Android Studio Preview support
    implementation(Libs.COMPOSE_UI_TOOLING_PREVIEW)
    debugImplementation(Libs.COMPOSE_UI_TOOLING)
    // Integration with activities
    implementation(Libs.ACTIVITY_COMPOSE)
    // Integration with ViewModels
    implementation(Libs.VIEWMODEL_COMPOSE)
    // Integration with LiveData
    implementation(Libs.RUNTIME_LIVEDATA_COMPOSE)
    // Material Design
    implementation(Libs.COMPOSE_MATERIAL)
    // Material Design 3
    implementation(Libs.COMPOSE_MATERIAL_3)
    // Pagination
    implementation(Libs.COMPOSE_PAGINATION)

    // Jetpack
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.FRAGMENT_KTX)
    // ViewModel
    implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
    // LiveData
    implementation(Libs.LIFECYCLE_LIVEDATA_KTX)
    // Navigation
    implementation(Libs.NAVIGATION_UI_KTX)
    implementation(Libs.NAVIGATION_FRAGMENT_KTX)
    // Room
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_KTX)
    kapt(Libs.ROOM_COMPILER)

    // Hilt
    implementation(Libs.HILT)
    kapt(Libs.HILT_DAGGER_COMPILER)
    kapt(Libs.HILT_COMPILER)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Retrofit
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON)
    implementation(Libs.OKHTTP3)
    implementation(Libs.OKHTTP3_LOGGING_INTERCEPTOR)

    // Gson
    implementation(Libs.GSON)

    // AppCenter
    implementation(Libs.APPCENTER_ANALYTICS)
    implementation(Libs.APPCENTER_CRASHES)

    // Timber
    implementation(Libs.TIMBER)

    // Glide
    implementation(Libs.GLIDE)

    // Apollo GraphQL
    implementation(Libs.APOLLO_GRAPH_QL)

    // System UI Controller
    implementation(Libs.SYSTEM_UI_CONTROLLER)

    // Unit testing
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.JUNIT_EXT)
    testImplementation(Libs.ARCH_CORE_TESTING)
    testImplementation(Libs.COROUTINES_TEST)
    testImplementation(Libs.HAMCREST)
    testImplementation(Libs.MOCKITO_CORE)
    testImplementation(Libs.MOCKITO_KOTLIN)

    // UI testing
    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.JUNIT_EXT)
    androidTestImplementation(Libs.TEST_RULES)
    androidTestImplementation(Libs.ESPRESSO_CORE)
    androidTestImplementation(Libs.ESPRESSO_CONTRIB)
    androidTestImplementation(Libs.COMPOSE_UI_TEST_JUNIT_4)
    debugImplementation(Libs.COMPOSE_UI_TEST_MANIFEST)
}

apollo {
    generateKotlinModels.set(true)
    service("service") {
        packageName.set(App.ID)
    }
}
