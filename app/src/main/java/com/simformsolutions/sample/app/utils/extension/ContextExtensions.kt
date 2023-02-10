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

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

/**
 * Get file name of the file [Uri]
 * @param uri of the file
 * @return file name
 */
fun Context.getFileName(uri: Uri): String? =
    contentResolver.query(uri, null, null, null, null)
        ?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToNext()
            it.getString(nameIndex)
        }
