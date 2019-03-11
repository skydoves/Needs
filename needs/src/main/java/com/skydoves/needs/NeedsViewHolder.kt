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

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_needs.view.*

/** NeedsViewHolder is an implementation of [BaseViewHolder] that has [NeedsItem] as data. */
internal class NeedsViewHolder(
  view: View,
  private val needsItemTheme: NeedsItemTheme? = null
)
  : BaseViewHolder(view) {

  private lateinit var needsItem: NeedsItem

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is NeedsItem) {
      needsItem = data
      drawItemUI()
    }
  }

  private fun drawItemUI() {
    itemView.run {
      needsItem.icon?.let {
        item_needs_image.visible(true)
        item_needs_image.setImageDrawable(it)
      } ?: let { item_needs_image.visible(false) }
      item_needs_title.text = needsItem.title
      item_needs_require.text = needsItem.require
      item_needs_description.text = needsItem.description

      needsItemTheme?.let {
        item_needs_title.applyTextForm(needsItemTheme.titleTextForm)
        item_needs_require.applyTextForm(needsItemTheme.requireTextForm)
        item_needs_description.applyTextForm(needsItemTheme.descriptionTextForm)
      }
    }
  }

  override fun onClick(v: View?) = Unit

  override fun onLongClick(v: View?) = false
}
