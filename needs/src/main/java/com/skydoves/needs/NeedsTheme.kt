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
import android.graphics.Typeface
import androidx.core.content.ContextCompat

/** creates an instance of [NeedsTheme] by [NeedsTheme.Builder] using kotlin dsl. */
fun needsTheme(context: Context, block: NeedsTheme.Builder.() -> Unit): NeedsTheme =
    NeedsTheme.Builder(context).apply(block).build()

/** NeedsTheme is an attribute class for changing [Needs] popup theme easily. */
class NeedsTheme(builder: Builder) {

  val backgroundColor = builder.backgroundColor
  val titleTextForm = builder.titleTextForm
  val descriptionTextForm = builder.descriptionTextForm
  val confirmTextForm = builder.confirmTextForm

  /** Builder class for creating [NeedsTheme]. */
  class Builder(context: Context) {
    @JvmField
    var backgroundColor = ContextCompat.getColor(context, R.color.white)
    @JvmField
    var titleTextForm = textForm(context) {
      textColor = ContextCompat.getColor(context, R.color.title)
      textSize = 18
      textStyle = Typeface.BOLD
    }
    @JvmField
    var descriptionTextForm = textForm(context) {
      textColor = ContextCompat.getColor(context, R.color.description)
      textSize = 12
      textStyle = Typeface.NORMAL
    }
    @JvmField
    var confirmTextForm = textForm(context) {
      textColor = ContextCompat.getColor(context, R.color.white)
      textSize = 18
      textStyle = Typeface.BOLD
    }

    fun setBackgroundColor(value: Int): Builder = apply { this.backgroundColor = value }
    fun setTitleTextForm(value: TextForm): Builder = apply { this.titleTextForm = value }
    fun setDescriptionTextForm(value: TextForm): Builder = apply { this.descriptionTextForm = value }
    fun setConfirmTextForm(value: TextForm): Builder = apply { this.confirmTextForm = value }
    fun build(): NeedsTheme {
      return NeedsTheme(this)
    }
  }
}
