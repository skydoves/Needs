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
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.skydoves.needs.annotations.Dp

@DslMarker
internal annotation class BulletFormDsl

/** creates an instance of [BulletForm] from [BulletForm.Builder] using kotlin dsl. */
@JvmSynthetic
@BulletFormDsl
fun bulletForm(context: Context, block: BulletForm.Builder.() -> Unit): BulletForm =
  BulletForm.Builder(context).apply(block).build()

/**
 * BulletForm is an attribute class what has some attributes about [NeedsItem]'s bullet point
 * for customizing popup texts easily.
 */
@BulletFormDsl
class BulletForm(builder: Builder) {

  val bulletSize = builder.bulletSize
  val bulletColor = builder.bulletColor
  val bulletPadding = builder.bulletPadding
  val bulletDrawable = builder.bulletDrawable

  /** Builder class for creating [BulletForm] */
  class Builder(private val context: Context) {
    @Px
    @JvmField
    var bulletSize: Int = context.dp2Px(4)

    @JvmField
    var bulletColor: Int = Color.GRAY

    @Dp
    @JvmField
    var bulletPadding: Int = context.dp2Px(6)

    @JvmField
    var bulletDrawable: Drawable? = GradientDrawable().apply {
      cornerRadius = 100f
      setColor(bulletColor)
      setSize(bulletSize, bulletSize)
    }

    fun setBulletSize(@Px value: Int) = apply {
      this.bulletSize = value
      (bulletDrawable as? GradientDrawable)?.apply { setSize(value, value) }
    }

    fun setBulletSizeResource(@DimenRes value: Int) = apply {
      setBulletSize(context.resources.getDimensionPixelSize(value))
    }

    fun setBulletColor(@ColorInt value: Int) = apply {
      this.bulletColor = value
      (bulletDrawable as? GradientDrawable)?.apply { setColor(value) }
    }

    fun setBulletColorResource(@ColorRes value: Int) = apply {
      setBulletColor(ContextCompat.getColor(context, value))
    }

    fun setBulletPadding(@Dp value: Int) = apply { this.bulletPadding = context.dp2Px(value) }

    fun setBulletDrawable(value: Drawable) = apply { this.bulletDrawable = value }
    fun setBulletDrawableResource(@DrawableRes value: Int) = apply {
      this.bulletDrawable = ResourcesCompat.getDrawable(context.resources, value, null)
    }

    fun build() = BulletForm(this)
  }
}
