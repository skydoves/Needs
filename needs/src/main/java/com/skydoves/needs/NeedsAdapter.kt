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
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.needs.databinding.NeedsLibraryItemNeedsBinding

/** NeedsAdapter is an implementation of [RecyclerView.Adapter] that has [NeedsItem] as items. */
internal class NeedsAdapter(
  private val needsItemTheme: NeedsItemTheme? = null
) : RecyclerView.Adapter<NeedsAdapter.NeedsViewHolder>() {

  private val needsItemList: MutableList<NeedsItem> = mutableListOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): NeedsViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = NeedsLibraryItemNeedsBinding.inflate(inflater, parent, false)
    return NeedsViewHolder(binding, needsItemTheme)
  }

  override fun onBindViewHolder(holder: NeedsViewHolder, position: Int) {
    holder.bind(needsItemList[position])
  }

  fun addItemList(needsItems: List<NeedsItem>) {
    this.needsItemList.addAll(needsItems)
    notifyDataSetChanged()
  }

  override fun getItemCount() = this.needsItemList.size

  class NeedsViewHolder(
    private val binding: NeedsLibraryItemNeedsBinding,
    private val needsItemTheme: NeedsItemTheme? = null
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(needsItem: NeedsItem) {
      with(binding) {
        needsItem.icon?.let {
          itemNeedsImage.visible(true)
          itemNeedsImage.setImageDrawable(it)
        } ?: let { itemNeedsImage.visible(false) }
        itemNeedsTitle.text = needsItem.title
        itemNeedsRequire.text = needsItem.require
        itemNeedsDescription.text = needsItem.description
        applyBulletOnText(binding.itemNeedsTitle)
        needsItemTheme?.let {
          itemNeedsTitle.applyTextForm(it.titleTextForm)
          itemNeedsRequire.applyTextForm(it.requireTextForm)
          itemNeedsDescription.applyTextForm(it.descriptionTextForm)
        }
      }
    }

    private fun applyBulletOnText(textView: TextView) {
      val bulletForm = needsItemTheme?.bulletForm ?: return
      textView.setCompoundDrawablesWithIntrinsicBounds(bulletForm.bulletDrawable, null, null, null)
      textView.compoundDrawablePadding = bulletForm.bulletPadding
    }
  }
}
