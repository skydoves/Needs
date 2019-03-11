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
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow

/** NeedsAdapter is an implementation of [BaseAdapter] that has [NeedsItem] as items. */
@Suppress("unused")
class NeedsAdapter(
  private val needsItemTheme: NeedsItemTheme? = null
) : BaseAdapter() {

  init {
    addSection(ArrayList<NeedsItem>())
  }

  fun addItem(needsItem: NeedsItem) {
    addItemOnSection(0, needsItem)
    notifyDataSetChanged()
  }

  fun addItemList(needsItems: List<NeedsItem>) {
    addItemListOnSection(0, needsItems)
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_needs

  override fun viewHolder(layout: Int, view: View) = NeedsViewHolder(view, needsItemTheme)
}
