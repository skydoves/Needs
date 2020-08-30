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

class DarkNeedsFactory : Needs.Factory() {

  override fun create(context: Context, lifecycle: LifecycleOwner): Needs {
    val titleForm = textForm {
      textSize = 18
      textStyle = Typeface.BOLD
      textColor = ContextCompat.getColor(context, R.color.black)
    }

    val theme = needsTheme(context) {
      backgroundColor = ContextCompat.getColor(context, R.color.background)
      titleTextForm = textForm {
        textSize = 18
        textColor = ContextCompat.getColor(context, R.color.white)
      }
      descriptionTextForm = textForm {
        textSize = 12
        textColor = ContextCompat.getColor(context, R.color.description)
      }
    }

    val itemTheme = needsItemTheme(context) {
      backgroundColor = ContextCompat.getColor(context, R.color.background)
      titleTextForm = textForm {
        textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        textSize = 16
      }
      descriptionTextForm = textForm {
        textColor = ContextCompat.getColor(context, R.color.description)
      }
    }

    return createNeeds(context) {
      setTitleIcon(ContextCompat.getDrawable(context, R.drawable.icon))
      setTitle("Permission instructions \nfor using this Android app.")
      setTitleTextForm(titleForm)
      setDescription(
        "The above accesses are used to better serve you. This application is available even if you do not agree to allow it."
      )
      setConfirm("Confirm")
      setBackgroundAlpha(0.7f)
      setLifecycleOwner(lifecycle)
      setNeedsTheme(theme)
      setNeedsItemTheme(itemTheme)
      setNeedsAnimation(NeedsAnimation.CIRCULAR)
      setDividerHeight(0.5f)
      addNeedsItem(
        NeedsItem(
          ContextCompat.getDrawable(context, R.drawable.ic_sd_storage_white_24dp),
          "SD Card",
          "(Required)",
          "Access photos, media, and files on device."
        )
      )
      addNeedsItem(
        NeedsItem(
          ContextCompat.getDrawable(context, R.drawable.ic_location_on_white_24dp),
          "Location",
          "(Required)",
          "Access this device's location."
        )
      )
      addNeedsItem(
        NeedsItem(
          ContextCompat.getDrawable(context, R.drawable.ic_camera_alt_white_24dp),
          "Camera",
          "(Optional)",
          "Take pictures and record video."
        )
      )
      addNeedsItem(
        NeedsItem(
          ContextCompat.getDrawable(context, R.drawable.ic_contacts_white_24dp),
          "Contact",
          "(Optional)",
          "Access this device's contacts."
        )
      )
      addNeedsItem(
        NeedsItem(
          ContextCompat.getDrawable(context, R.drawable.ic_sms_white_24dp),
          "SMS",
          "(Optional)",
          "Send and view SMS messages."
        )
      )
    }
  }
}
