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

package com.skydoves.needsdemo

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.needs.Needs
import com.skydoves.needs.NeedsAnimation
import com.skydoves.needs.NeedsItem

object NeedsUtils {

  fun getNeedsStyle0(baseContext: Context, lifecycleOwner: LifecycleOwner): Needs {
    return Needs.Builder(baseContext)
      .setTitle("Permission instructions \nfor using this Android app.")
      .setDescription(
        "The above accesses are used to better serve you. This application is available even if you do not agree to allow it.")
      .setConfirm("Confirm")
      .setBackgroundAlpha(0.6f)
      .setLifecycleOwner(lifecycleOwner)
      .setNeedsAnimation(NeedsAnimation.FADE)
      .addNeedsItem(
        NeedsItem(null, "· SD Card", "(Required)", "   Access photos, media, and files on device."))
      .addNeedsItem(
        NeedsItem(null, "· Location", "(Required)", "   Access this device's location."))
      .addNeedsItem(NeedsItem(null, "· Camera", "(Optional)", "   Take pictures and record video."))
      .addNeedsItem(NeedsItem(null, "· Contact", "(Optional)", "   Access this device's contacts."))
      .addNeedsItem(NeedsItem(null, "· SMS", "(Optional)", "   Send and view SMS messages."))
      .build()
  }
}
