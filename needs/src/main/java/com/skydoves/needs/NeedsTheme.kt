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

@file:Suppress("unused")

package com.skydoves.needs

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

@DslMarker
annotation class NeedsThemeDsl

/** creates an instance of [NeedsTheme] by [NeedsTheme.Builder] using kotlin dsl. */
fun needsTheme(context: Context, block: NeedsTheme.Builder.() -> Unit): NeedsTheme =
  NeedsTheme.Builder(context).apply(block).build()

/** NeedsTheme is an attribute class for changing [Needs] popup theme easily. */
class NeedsTheme(builder: Builder) {

  @ColorInt val backgroundColor = builder.backgroundColor
  val titleTextForm = builder.titleTextForm
  val descriptionTextForm = builder.descriptionTextForm
  val confirmTextForm = builder.confirmTextForm

  /** Builder class for creating [NeedsTheme]. */
  @NeedsThemeDsl
  class Builder(val context: Context) {
    @JvmField @ColorInt
    var backgroundColor = Color.WHITE
    @JvmField
    var titleTextForm = textForm {
      textColor = Color.parseColor("#333333")
      textSize = 18
      textStyle = Typeface.BOLD
    }
    @JvmField
    var descriptionTextForm = textForm {
      textColor = Color.parseColor("#FAFAFA")
      textSize = 12
      textStyle = Typeface.NORMAL
    }
    @JvmField
    var confirmTextForm = textForm {
      textColor = Color.WHITE
      textSize = 18
      textStyle = Typeface.BOLD
    }

    fun setBackgroundColor(@ColorInt value: Int): Builder = apply { this.backgroundColor = value }
    fun setBackgroundColorResource(@ColorRes value: Int): Builder = apply {
      this.backgroundColor = ContextCompat.getColor(context, value)
    }

    fun setTitleTextForm(value: TextForm): Builder = apply { this.titleTextForm = value }
    fun setDescriptionTextForm(value: TextForm): Builder = apply {
      this.descriptionTextForm = value
    }

    fun setConfirmTextForm(value: TextForm): Builder = apply { this.confirmTextForm = value }
    fun build(): NeedsTheme {
      return NeedsTheme(this)
    }
  }
}
