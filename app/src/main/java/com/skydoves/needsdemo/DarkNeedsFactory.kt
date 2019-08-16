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
    val titleForm = textForm(context) {
      textSize = 18
      textStyle = Typeface.BOLD
      textColor = ContextCompat.getColor(context, R.color.black)
    }

    val theme = needsTheme(context) {
      backgroundColor = ContextCompat.getColor(context, R.color.background)
      titleTextForm = textForm(context) {
        textSize = 18
        textColor = ContextCompat.getColor(context, R.color.white)
      }
      descriptionTextForm = textForm(context) {
        textSize = 12
        textColor = ContextCompat.getColor(context, R.color.description)
      }
    }

    val itemTheme = needsItemTheme(context) {
      backgroundColor = ContextCompat.getColor(context, R.color.background)
      titleTextForm = textForm(context) {
        textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        textSize = 16
      }
      descriptionTextForm = textForm(context) {
        textColor = ContextCompat.getColor(context, R.color.description)
      }
    }

    return createNeeds(context) {
      titleIcon = ContextCompat.getDrawable(context, R.drawable.icon)!!
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
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(context, R.drawable.ic_sd_storage_white_24dp), "SD Card", "(Required)", "Access photos, media, and files on device."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(context, R.drawable.ic_location_on_white_24dp), "Location", "(Required)", "Access this device's location."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(context, R.drawable.ic_camera_alt_white_24dp), "Camera", "(Optional)", "Take pictures and record video."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(context, R.drawable.ic_contacts_white_24dp), "Contact", "(Optional)", "Access this device's contacts."))
      addNeedsItem(NeedsItem(ContextCompat.getDrawable(context, R.drawable.ic_sms_white_24dp), "SMS", "(Optional)", "Send and view SMS messages."))
    }
  }
}