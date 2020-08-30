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

import android.graphics.Color
import android.graphics.Typeface
import androidx.annotation.ColorInt
import com.skydoves.needs.annotations.Sp

@DslMarker
annotation class TextFormDsl

/** creates an instance of [TextForm] from [TextForm.Builder] using kotlin dsl. */
fun textForm(block: TextForm.Builder.() -> Unit): TextForm =
  TextForm.Builder().apply(block).build()

/**
 * TextFrom is an attribute class what has some attributes about TextView
 * for customizing popup texts easily.
 */
@TextFormDsl
class TextForm(builder: Builder) {

  @Sp
  val textSize = builder.textSize
  val textColor = builder.textColor
  val textStyle = builder.textStyle

  /** Builder class for [TextForm]. */
  class Builder {
    @Sp
    @JvmField
    var textSize = 14

    @JvmField
    @ColorInt
    var textColor = Color.WHITE

    @JvmField
    var textStyle = Typeface.NORMAL

    fun setTextSize(@Sp value: Int): Builder = apply { this.textSize = value }
    fun setTextColor(@ColorInt value: Int): Builder = apply { this.textColor = value }
    fun setTextStyle(value: Int): Builder = apply { this.textStyle = value }
    fun build(): TextForm {
      return TextForm(this)
    }
  }
}
