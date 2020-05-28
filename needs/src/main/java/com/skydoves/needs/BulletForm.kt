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
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

@DslMarker
annotation class BulletFormDsl

/** creates an instance of [BulletForm] from [BulletForm.Builder] using kotlin dsl. */
@BulletFormDsl
fun bulletForm(context: Context, block: BulletForm.Builder.() -> Unit): BulletForm =
  BulletForm.Builder(context).apply(block).build()

/**
 * BulletForm is an attribute class what has some attributes about [NeedsItem]'s bullet point
 * for customizing popup texts easily.
 */
@BulletFormDsl
class BulletForm(builder: Builder) {

  /** Builder class for creating [BulletForm] */
  class Builder(private val context: Context) {
    @JvmField
    var bulletSize: Int = context.dp2Px(3)

    @JvmField
    var bulletColor: Int = Color.GRAY

    @JvmField
    var bulletDrawable: Drawable? = GradientDrawable().apply {
      cornerRadius = 100f
      setColor(bulletColor)
      setSize(bulletSize, bulletSize)
    }

    fun setBulletSize(value: Int) = apply { this.bulletSize = value }
    fun setBulletSizeResource(@DimenRes value: Int) = apply {
      this.bulletSize = context.resources.getDimensionPixelSize(value)
    }

    fun setBulletColor(@ColorInt value: Int) = apply { this.bulletColor = value }
    fun setBulletColorResource(@ColorRes value: Int) = apply {
      this.bulletColor = ContextCompat.getColor(context, value)
    }

    fun setDrawable(value: Drawable) = apply { this.bulletDrawable = value }
    fun setDrawableResource(@DrawableRes value: Int) = apply {
      this.bulletDrawable = ResourcesCompat.getDrawable(context.resources, value, null)
    }

    fun build() = BulletForm(this)
  }
}
