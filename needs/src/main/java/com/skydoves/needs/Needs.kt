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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_background.view.*
import kotlinx.android.synthetic.main.layout_body.view.*

@DslMarker
annotation class NeedsDsl

/** creates an instance of [Needs] by [Needs.Builder] using kotlin dsl. */
@NeedsDsl
fun createNeeds(context: Context, block: Needs.Builder.() -> Unit): Needs =
  Needs.Builder(context).apply(block).build()

/** Needs implements showing and dismissing popup with background, animations. */
@Suppress("MemberVisibilityCanBePrivate")
@SuppressLint("InflateParams")
class Needs(
  private val context: Context,
  private val builder: Builder
) : LifecycleObserver {

  private val backgroundView: View
  private val backgroundWindow: PopupWindow
  private val bodyView: View
  private val bodyWindow: PopupWindow
  private lateinit var adapter: NeedsAdapter
  private var onConfirmListener: OnConfirmListener? = null
  var isShowing = false
    private set
  private var showTimes: Int = 1
  private var preferenceName: String? = null
  private val needsPreferenceManager = NeedsPreferenceManager(context).getInstance()

  init {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    this.backgroundView = inflater.inflate(R.layout.layout_background, null)
    this.backgroundWindow = PopupWindow(
      backgroundView,
      FrameLayout.LayoutParams.MATCH_PARENT,
      FrameLayout.LayoutParams.MATCH_PARENT
    )
    this.bodyView = inflater.inflate(R.layout.layout_body, null)
    this.bodyWindow = PopupWindow(
      bodyView,
      context.displaySize().x - context.dp2Px(20) * 2,
      LinearLayout.LayoutParams.WRAP_CONTENT
    )
    createByBuilder()
  }

  private fun createByBuilder() {
    with(bodyView) {
      title.text = builder.title
      description.text = builder.description
      confirm.text = builder.confirm
      confirm.setBackgroundColor(builder.confirmBackgroundColor)
      confirm_wrapper.visible(builder.confirmVisible)
    }

    with(builder) {
      titleIcon?.let {
        bodyView.title_icon.setImageDrawable(it)
        bodyView.title_icon.visible(true)
      }
      onConfirmListener?.let { setOnConfirmListener(it) }
      lifecycleOwner?.lifecycle?.addObserver(this@Needs)
    }

    initializeTextForm()
    initializeRecyclerView()
    initializeDivider()
    initializeBackground()
    initializeTheme()
    initializePreferences()

    bodyWindow.width = context.displaySize().x - context.dp2Px(builder.padding) * 2
  }

  private fun initializeTextForm() {
    with(builder) {
      titleTextForm?.let {
        bodyView.title.applyTextForm(it)
      }
      descriptionTextForm?.let {
        bodyView.description.applyTextForm(it)
      }
      confirmTextForm?.let {
        bodyView.confirm.applyTextForm(it)
      }
    }
  }

  private fun initializeRecyclerView() {
    builder.listAdapter?.let {
      bodyView.recyclerView.adapter = it
    } ?: let {
      adapter = NeedsAdapter(builder.needsItemTheme)
      bodyView.recyclerView.adapter = adapter
      adapter.addItemList(builder.needsList)
    }
    bodyView.recyclerView.layoutManager = LinearLayoutManager(context)
    val params = bodyView.recyclerView.layoutParams as LinearLayout.LayoutParams
    params.height = context.dp2Px(builder.listHeight)
    bodyView.recyclerView.layoutParams = params
  }

  private fun initializeDivider() {
    with(bodyView) {
      divider_top.setBackgroundColor(builder.dividerColor)
      divider_top.visible(builder.dividerVisible)
      divider_top.layoutParams.height = context.dp2Px(builder.dividerHeight)
      divider_bottom.setBackgroundColor(builder.dividerColor)
      divider_bottom.visible(builder.dividerVisible)
      divider_bottom.layoutParams.height = context.dp2Px(builder.dividerHeight)
    }
  }

  private fun initializeBackground() {
    builder.background?.let { backgroundView.overlap.background = it }
    backgroundView.overlap.setBackgroundColor(builder.backgroundColor)
    backgroundView.overlap.alpha = builder.backgroundAlpha
  }

  private fun initializeTheme() {
    builder.needsTheme?.let {
      with(bodyView) {
        setBackgroundColor(it.backgroundColor)
        title.applyTextForm(it.titleTextForm)
        description.applyTextForm(it.descriptionTextForm)
        confirm.applyTextForm(it.confirmTextForm)
      }
    }
  }

  private fun initializePreferences() {
    preferenceName = builder.preferenceName
    showTimes = builder.showTimes
  }

  private fun applyWindowAnimation() {
    when (builder.needsAnimation) {
      NeedsAnimation.ELASTIC -> bodyWindow.animationStyle = R.style.Elastic
      NeedsAnimation.CIRCULAR -> {
        bodyWindow.contentView.circularRevealed()
        bodyWindow.animationStyle = R.style.NormalDispose
      }
      NeedsAnimation.FADE -> {
        bodyWindow.animationStyle = R.style.Fade
        backgroundWindow.animationStyle = R.style.Fade
      }
      else -> bodyWindow.animationStyle = R.style.Normal
    }
  }

  fun setOnConfirmListener(onConfirmListener: OnConfirmListener) {
    bodyView.confirm.setOnClickListener { onConfirmListener.onConfirm() }
  }

  fun setOnConfirmListener(block: () -> Unit) {
    bodyView.confirm.setOnClickListener { block() }
  }

  /** shows the popup menu to the center. */
  fun show(view: View) {
    if (!isShowing) {
      preferenceName?.let {
        if (needsPreferenceManager.shouldShowUP(it, showTimes)) {
          needsPreferenceManager.putIncrementedTimes(it)
        } else return
      }

      applyWindowAnimation()
      backgroundWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
      bodyWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
      isShowing = true
    }
  }

  /** dismiss the popup menu. */
  fun dismiss() {
    if (isShowing) {
      backgroundWindow.dismiss()
      bodyWindow.dismiss()
      isShowing = false
    }
  }

  /** dismiss automatically when lifecycle owner is destroyed. */
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() {
    dismiss()
  }

  /** Builder class for creating [Needs]. */
  @Suppress("NOTHING_TO_INLINE")
  @NeedsDsl
  class Builder(private val context: Context) {
    @JvmField
    var titleIcon: Drawable? = null
    @JvmField
    var title: String = "Title"
    @JvmField
    var titleTextForm: TextForm? = null
    @JvmField
    var description: String = "description"
    @JvmField
    var descriptionTextForm: TextForm? = null
    @ColorInt
    @JvmField
    var confirmBackgroundColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
    @JvmField
    var confirm: String = "Confirm"
    @JvmField
    var confirmTextForm: TextForm? = null
    @JvmField
    var onConfirmListener: OnConfirmListener? = null
    @JvmField
    var confirmVisible: Boolean = true
    @JvmField
    var padding: Int = 20
    @JvmField
    var listAdapter: RecyclerView.Adapter<*>? = null
    @JvmField
    var listHeight: Int = 240
    @JvmField
    val needsList = ArrayList<NeedsItem>()
    @ColorInt
    @JvmField
    var dividerColor: Int = ContextCompat.getColor(context, R.color.divider)
    @JvmField
    var dividerVisible: Boolean = true
    @JvmField
    var dividerHeight: Float = 0.8f
    @JvmField
    var background: Drawable? = null
    @ColorInt
    @JvmField
    var backgroundColor: Int = Color.BLACK
    @JvmField
    var backgroundAlpha: Float = 0.6f
    @JvmField
    var lifecycleOwner: LifecycleOwner? = null
    @JvmField
    var needsTheme: NeedsTheme? = null
    @JvmField
    var needsItemTheme: NeedsItemTheme? = null
    @JvmField
    var needsAnimation: NeedsAnimation = NeedsAnimation.NONE
    @JvmField
    var preferenceName: String? = null
    @JvmField
    var showTimes: Int = 1

    fun setTitleIcon(drawable: Drawable): Builder = apply { this.titleIcon = drawable }
    fun setTitle(value: String): Builder = apply { this.title = value }
    fun setTitleTextForm(value: TextForm): Builder = apply { this.titleTextForm = value }
    fun setDescription(value: String): Builder = apply { this.description = value }
    fun setDescriptionTextForm(value: TextForm): Builder = apply { this.descriptionTextForm = value }
    fun setConfirmBackgroundColor(@ColorInt value: Int): Builder = apply { this.confirmBackgroundColor = value }
    fun setConfirm(value: String): Builder = apply { this.confirm = value }
    fun setConfirmTextForm(value: TextForm): Builder = apply { this.confirmTextForm = value }
    fun setConfirmVisible(value: Boolean): Builder = apply { this.confirmVisible = value }
    fun setListAdapter(value: RecyclerView.Adapter<*>): Builder = apply { this.listAdapter = value }
    fun setListHeight(value: Int): Builder = apply { this.listHeight = value }
    fun setPadding(value: Int): Builder = apply { this.padding = value }
    fun addNeedsItem(value: NeedsItem): Builder = apply { this.needsList.add(value) }
    fun addNeedsItemList(value: List<NeedsItem>): Builder = apply { this.needsList.addAll(value) }
    fun setBackground(value: Drawable): Builder = apply { this.background = value }
    fun setBackgroundColor(@ColorInt value: Int): Builder = apply { this.backgroundColor = value }
    fun setBackgroundAlpha(value: Float): Builder = apply { this.backgroundAlpha = value }
    fun setDividerColor(@ColorInt value: Int): Builder = apply { this.dividerColor = value }
    fun setDividerVisible(value: Boolean): Builder = apply { this.dividerVisible = value }
    fun setDividerHeight(value: Float): Builder = apply { this.dividerHeight = value }
    fun setLifecycleOwner(value: LifecycleOwner): Builder = apply { this.lifecycleOwner = value }
    fun setNeedsTheme(value: NeedsTheme): Builder = apply { this.needsTheme = value }
    fun setNeedsItemTheme(value: NeedsItemTheme): Builder = apply { this.needsItemTheme = value }
    fun setNeedsAnimation(value: NeedsAnimation): Builder = apply { this.needsAnimation = value }
    fun setPreferenceName(value: String): Builder = apply { this.preferenceName = value }
    fun setShowTime(value: Int): Builder = apply { this.showTimes = value }
    fun setOnConfirmListener(value: OnConfirmListener): Builder = apply { this.onConfirmListener = value }
    inline fun setOnConfirmListener(noinline block: () -> Unit): Builder = apply {
      this.onConfirmListener = object : OnConfirmListener {
        override fun onConfirm() {
          block()
        }
      }
    }

    fun build(): Needs = Needs(context, this)
  }
}
