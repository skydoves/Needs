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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_needs.view.item_needs_description
import kotlinx.android.synthetic.main.item_needs.view.item_needs_image
import kotlinx.android.synthetic.main.item_needs.view.item_needs_require
import kotlinx.android.synthetic.main.item_needs.view.item_needs_title

/** NeedsAdapter is an implementation of [RecyclerView.Adapter] that has [NeedsItem] as items. */
@Suppress("unused")
internal class NeedsAdapter(
  private val needsItemTheme: NeedsItemTheme? = null
) : RecyclerView.Adapter<NeedsAdapter.NeedsViewHolder>() {

  private val needsItemList: MutableList<NeedsItem> = mutableListOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): NeedsViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return NeedsViewHolder(inflater.inflate(R.layout.item_needs, parent, false))
  }

  override fun onBindViewHolder(holder: NeedsViewHolder, position: Int) {
    val needsItem = this.needsItemList[position]
    holder.itemView.run {
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

  fun addItem(needsItem: NeedsItem) {
    this.needsItemList.add(needsItem)
    notifyDataSetChanged()
  }

  fun addItemList(needsItems: List<NeedsItem>) {
    this.needsItemList.addAll(needsItems)
    notifyDataSetChanged()
  }

  override fun getItemCount() = this.needsItemList.size

  class NeedsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
