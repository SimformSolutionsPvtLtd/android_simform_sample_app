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
package com.simformsolutions.sample.app.utils.pref

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Shared preference delegated property for float values. Use this class to store and retrive
 * float value from shared preference just like a normal variable.
 * Usage:
 * ```
 * // Lazy variable for shared preference
 * val lazySharedPreferences = lazy { getSharedPreferences(...) }
 * // Delegated property
 * var percentComplete by FloatPreference(lazySharedPreferences, key, defaultValue)
 *
 * // Set value
 * percentComplete = 100f
 * // Get value
 * if (percentComplete == 100f)
 *     showTutorialCompleteDialog()
 * else
 *     showTutorialIncompleteDialog()
 * ```
 */
class FloatPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Float
) : ReadWriteProperty<Any, Float> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Float {
        return preferences.value.getFloat(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
        preferences.value.edit { putFloat(name, value) }
    }
}
