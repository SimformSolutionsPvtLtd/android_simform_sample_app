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

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

/**
 * This fun is used to open dialog fragment
 * @receiver Fragment
 * @param dialogFragment DialogFragment
 */
fun Fragment.showDialogFragment(dialogFragment: DialogFragment, init: Bundle.() -> Unit = {}) {
    val bundle = Bundle()
    bundle.init()
    dialogFragment.arguments = bundle
    dialogFragment.show(parentFragmentManager, dialogFragment::class.java.simpleName)
}

/**
 * @receiver Context
 * @param options Bundle?
 * @param init Intent.() -> Unit
 */
inline fun <reified T : Any> Fragment.launchActivityWithResult(
    options: Bundle? = null,
    requestCode: Int,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(requireContext())
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

/**
 * Launch activity extension with optional bundles.
 * @receiver Context
 * @param options Bundle?
 * @param init Intent.() -> Unit
 */
inline fun <reified T : Any> Fragment.launchActivity(options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(requireActivity())
    intent.init()
    startActivity(intent, options)
}
