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

package com.skydoves.needsdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.needs.Needs
import com.skydoves.needs.OnConfirmListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var needs: Needs

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    this.needs = NeedsUtils.getNeedsStyle1(this, this)

    button0.setOnClickListener {
      needs = NeedsUtils.getNeedsStyle0(this, this)
      setOnConfirmListener()
      needs.show(main)
    }

    button1.setOnClickListener {
      needs = NeedsUtils.getNeedsStyle1(this, this)
      setOnConfirmListener()
      needs.show(main)
    }
  }

  private fun setOnConfirmListener() {
    needs.setOnConfirmListener(object : OnConfirmListener {
      override fun onConfirm() {
        Toast.makeText(baseContext, "Confirmed!", Toast.LENGTH_SHORT).show()
        needs.dismiss()
      }
    })
  }

  override fun onBackPressed() {
    if (needs.isShowing) {
      needs.dismiss()
    } else {
      super.onBackPressed()
    }
  }
}
