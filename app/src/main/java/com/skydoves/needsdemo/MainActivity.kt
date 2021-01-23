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
import com.skydoves.needs.needs
import com.skydoves.needsdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var needs0: Needs
  private val needs1 by needs<DarkNeedsFactory>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {

      needs1.setOnConfirmListener { needs1.dismiss() }
      needs1.show(button0)

      needs0 = DarkNeedsFactory().create(this@MainActivity, this@MainActivity)

      button0.setOnClickListener {
        needs0 = NeedsUtils.getNeedsStyle0(this@MainActivity, this@MainActivity)
        setOnConfirmListener()
        needs0.show(main)
      }

      button1.setOnClickListener {
        needs0 = DarkNeedsFactory().create(this@MainActivity, this@MainActivity)
        setOnConfirmListener()
        needs0.show(main)
      }
    }
  }

  private fun setOnConfirmListener() {
    needs0.setOnConfirmListener {
      Toast.makeText(baseContext, "Confirmed!", Toast.LENGTH_SHORT).show()
      needs0.dismiss()
    }
  }

  override fun onBackPressed() {
    if (needs0.isShowing) {
      needs0.dismiss()
    } else {
      super.onBackPressed()
    }
  }
}
