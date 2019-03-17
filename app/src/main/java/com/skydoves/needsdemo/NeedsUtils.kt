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
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.skydoves.needs.Needs
import com.skydoves.needs.NeedsAnimation
import com.skydoves.needs.NeedsItem
import com.skydoves.needs.createNeeds
import com.skydoves.needs.needsItemTheme
import com.skydoves.needs.needsTheme
import com.skydoves.needs.textForm

object NeedsUtils {
  fun getNeedsStyle0(baseContext: Context, lifecycleOwner: LifecycleOwner): Needs {
    return Needs.Builder(baseContext)
      .setTitle("Permission instructions \nfor using this Android app.")
      .setDescription("The above accesses are used to better serve you. This application is available even if you do not agree to allow it.")
      .setConfirm("Confirm")
      .setBackgroundAlpha(0.6f)
      .setLifecycleOwner(lifecycleOwner)
      .setNeedsAnimation(NeedsAnimation.FADE)
      .addNeedsItem(NeedsItem(null, "· SD Card", "(Required)", "   Access photos, media, and files on device."))
      .addNeedsItem(NeedsItem(null, "· Location", "(Required)", "   Access this device's location."))
      .addNeedsItem(NeedsItem(null, "· Camera", "(Optional)", "   Take pictures and record video."))
      .addNeedsItem(NeedsItem(null, "· Contact", "(Optional)", "   Access this device's contacts."))
      .addNeedsItem(NeedsItem(null, "· SMS", "(Optional)", "   Send and view SMS messages."))
      .build()
  }

  fun getNeedsStyle1(baseContext: Context, lifecycle: LifecycleOwner): Needs {
    val titleForm = textForm(baseContext) {
      textSize = 18
      textStyle = Typeface.BOLD
      textColor = ContextCompat.getColor(baseContext, R.color.black)
    }

    val theme = needsTheme(baseContext) {
      backgroundColor = ContextCompat.getColor(baseContext, R.color.background)
      titleTextForm = textForm(baseContext) {
        textSize = 18
        textColor = ContextCompat.getColor(baseContext, R.color.white)
      }
      descriptionTextForm = textForm(baseContext) {
        textSize = 12
        textColor = ContextCompat.getColor(baseContext, R.color.description)
      }
    }

    val itemTheme = needsItemTheme(baseContext) {
      backgroundColor = ContextCompat.getColor(baseContext, R.color.background)
      titleTextForm = textForm(baseContext) {
        textColor = ContextCompat.getColor(baseContext, R.color.colorPrimaryDark)
        textSize = 16
      }
      descriptionTextForm = textForm(baseContext) {
        textColor = ContextCompat.getColor(baseContext, R.color.description)
      }
    }

    return createNeeds(baseContext) {
      titleIcon = ContextCompat.getDrawable(baseContext, R.drawable.icon)!!
      title = "Permission instructions \nfor using this Android app."
      titleTextForm = titleForm
      description = "The above accesses are used to better serve you. This application is available even if you do not agree to allow it."
      confirm = "Confirm"
      backgroundAlpha = 0.6f
      lifecycleOwner = lifecycle
      needsTheme = theme
      needsItemTheme = itemTheme
      needsAnimation = NeedsAnimation.CIRCULAR
      dividerHeight = 0.6f
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(baseContext, R.drawable.ic_sd_storage_white_24dp), "SD Card", "(Required)", "Access photos, media, and files on device."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(baseContext, R.drawable.ic_location_on_white_24dp), "Location", "(Required)", "Access this device's location."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(baseContext, R.drawable.ic_camera_alt_white_24dp), "Camera", "(Optional)", "Take pictures and record video."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(baseContext, R.drawable.ic_contacts_white_24dp), "Contact", "(Optional)", "Access this device's contacts."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(baseContext, R.drawable.ic_sms_white_24dp), "SMS", "(Optional)", "Send and view SMS messages."))
    }
  }
}
