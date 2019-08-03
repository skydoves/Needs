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

@DslMarker
annotation class NeedsItemThemeDsl

/** creates an instance of [NeedsItemTheme] by [NeedsItemTheme.Builder] using kotlin dsl. */
fun needsItemTheme(context: Context, block: NeedsItemTheme.Builder.() -> Unit): NeedsItemTheme =
  NeedsItemTheme.Builder(context).apply(block).build()

/** NeedsItemTheme is an attribute class for changing item theme easily. */
class NeedsItemTheme(builder: Builder) {

  val backgroundColor = builder.backgroundColor
  val titleTextForm = builder.titleTextForm
  val requireTextForm = builder.requireTextForm
  val descriptionTextForm = builder.descriptionTextForm

  /** Builder class for creating [NeedsItemTheme]. */
  @NeedsItemThemeDsl
  class Builder(context: Context) {
    @JvmField
    var backgroundColor = ContextCompat.getColor(context, R.color.white)
    @JvmField
    var titleTextForm = textForm(context) {
      textColor = ContextCompat.getColor(context, R.color.title)
      textSize = 16
      textStyle = Typeface.BOLD
    }
    @JvmField
    var requireTextForm = textForm(context) {
      textColor = ContextCompat.getColor(context, R.color.colorPrimary)
      textSize = 16
      textStyle = Typeface.NORMAL
    }
    @JvmField
    var descriptionTextForm = textForm(context) {
      textColor = ContextCompat.getColor(context, R.color.description)
      textSize = 12
      textStyle = Typeface.NORMAL
    }

    fun setBackgroundColor(value: Int): Builder = apply { this.backgroundColor = value }
    fun setTitleTextForm(value: TextForm): Builder = apply { this.titleTextForm = value }
    fun setRequireTextForm(value: TextForm): Builder = apply { this.requireTextForm = value }
    fun descriptionTextForm(value: TextForm): Builder = apply { this.descriptionTextForm = value }
    fun build(): NeedsItemTheme {
      return NeedsItemTheme(this)
    }
  }
}
