/*
 * Copyright (C) 2019 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.needs

import android.content.Context
import android.content.SharedPreferences

@Suppress("PrivatePropertyName")
class NeedsPreferenceManager(context: Context, times: Int = 1) {

  private val SHOWED_UP = "SHOWED_UP"

  init {
    needsPreferenceManager = this
    sharedPreferenceManager = context.getSharedPreferences("com.skydoves.needs", Context.MODE_PRIVATE)
  }

  /** get a singleton instance of the [NeedsPreferenceManager]. */
  fun getInstance(): NeedsPreferenceManager {
    return needsPreferenceManager
  }

  /** gets show-up times from the preference. */
  fun getTimes(name: String): Int {
    return sharedPreferenceManager.getInt(SHOWED_UP + name, 0)
  }

  /** puts show-up times to the preference.  */
  fun putTimes(name: String, times: Int): Int {
    return sharedPreferenceManager.getInt(SHOWED_UP + name, times)
  }

  companion object {
    lateinit var needsPreferenceManager: NeedsPreferenceManager
    lateinit var sharedPreferenceManager: SharedPreferences
  }
}
