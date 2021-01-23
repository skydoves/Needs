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
internal annotation class NeedsItemThemeDsl

/** creates an instance of [NeedsItemTheme] by [NeedsItemTheme.Builder] using kotlin dsl. */
@JvmSynthetic
@NeedsItemThemeDsl
inline fun needsItemTheme(context: Context, block: NeedsItemTheme.Builder.() -> Unit): NeedsItemTheme =
  NeedsItemTheme.Builder(context).apply(block).build()

/** NeedsItemTheme is an attribute class for changing item theme easily. */
class NeedsItemTheme(builder: Builder) {

  @ColorInt
  val backgroundColor = builder.backgroundColor
  val bulletForm = builder.bulletForm
  val titleTextForm = builder.titleTextForm
  val requireTextForm = builder.requireTextForm
  val descriptionTextForm = builder.descriptionTextForm

  /** Builder class for creating [NeedsItemTheme]. */
  @NeedsItemThemeDsl
  class Builder(val context: Context) {
    @ColorInt
    @JvmField
    @set:JvmSynthetic
    var backgroundColor = Color.WHITE

    @JvmField
    @set:JvmSynthetic
    var bulletForm: BulletForm? = null

    @JvmField
    @set:JvmSynthetic
    var titleTextForm = textForm {
      textColor = Color.parseColor("#333333")
      textSize = 16
      textStyle = Typeface.BOLD
    }

    @JvmField
    @set:JvmSynthetic
    var requireTextForm = textForm {
      textColor = context.accentColor()
      textSize = 16
      textStyle = Typeface.NORMAL
    }

    @JvmField
    @set:JvmSynthetic
    var descriptionTextForm = textForm {
      textColor = Color.parseColor("#FAFAFA")
      textSize = 12
      textStyle = Typeface.NORMAL
    }

    fun setBackgroundColor(@ColorInt value: Int): Builder = apply { this.backgroundColor = value }
    fun setBackgroundColorResource(@ColorRes value: Int): Builder = apply {
      this.backgroundColor = ContextCompat.getColor(context, value)
    }

    fun setBulletForm(value: BulletForm): Builder = apply { this.bulletForm = value }
    fun setTitleTextForm(value: TextForm): Builder = apply { this.titleTextForm = value }
    fun setRequireTextForm(value: TextForm): Builder = apply { this.requireTextForm = value }
    fun descriptionTextForm(value: TextForm): Builder = apply { this.descriptionTextForm = value }
    fun build(): NeedsItemTheme {
      return NeedsItemTheme(this)
    }
  }
}
