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
import com.skydoves.needs.databinding.ItemNeedsBinding

/** NeedsAdapter is an implementation of [RecyclerView.Adapter] that has [NeedsItem] as items. */
@Suppress("unused")
internal class NeedsAdapter(
  private val needsItemTheme: NeedsItemTheme? = null
) : RecyclerView.Adapter<NeedsAdapter.NeedsViewHolder>() {

  private val needsItemList: MutableList<NeedsItem> = mutableListOf()

  private lateinit var binding: ItemNeedsBinding

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): NeedsViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    binding = ItemNeedsBinding.inflate(inflater, parent, false)
    return NeedsViewHolder(binding.root)
  }

  override fun onBindViewHolder(holder: NeedsViewHolder, position: Int) {
    val needsItem = this.needsItemList[position]
    holder.itemView.run {
      needsItem.icon?.let {
        binding.itemNeedsImage.visible(true)
        binding.itemNeedsImage.setImageDrawable(it)
      } ?: let { binding.itemNeedsImage.visible(false) }
      binding.itemNeedsTitle.text = needsItem.title
      binding.itemNeedsRequire.text = needsItem.require
      binding.itemNeedsDescription.text = needsItem.description

      needsItemTheme?.let {
        binding.itemNeedsTitle.applyTextForm(it.titleTextForm)
        binding.itemNeedsRequire.applyTextForm(it.requireTextForm)
        binding.itemNeedsDescription.applyTextForm(it.descriptionTextForm)
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
