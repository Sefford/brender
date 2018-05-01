/*
 * Copyright (C) 2015 Saúl Díaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sefford.brender.data

import android.widget.Filter
import com.sefford.brender.components.Renderable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class FilterableAdapterDataTest {
    @Mock
    lateinit var element: Renderable

    lateinit var master: MutableList<Renderable>
    lateinit var filtered: MutableList<Renderable>

    lateinit var adapterData: FilterableAdapterData

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        master = mutableListOf(element, element, element)

        filtered = ArrayList()
        filtered.add(element)

        adapterData = object : FilterableAdapterData(master) {
            override val viewTypeCount: Int
                get() = 0

            override fun notifyDataSetChanged() {

            }

            override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
                val results = Filter.FilterResults()
                results.count = filtered.size
                results.values = filtered
                return results
            }
        }
        adapterData.filter("")
    }

    @Test
    @Throws(Exception::class)
    fun testSize() {
        assertEquals(master.size.toLong(), adapterData.size().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemId() {
        assertEquals(EXPECTED_POS.toLong(), adapterData.getItemId(EXPECTED_POS))
    }

    @Test
    @Throws(Exception::class)
    fun testGetItem() {
        assertEquals(element, adapterData.getItem(EXPECTED_POS))
    }

    @Test
    @Throws(Exception::class)
    fun testGetFilter() {
        assertEquals(adapterData, adapterData.filter)
    }

    companion object {

        private val EXPECTED_POS = 1
    }
}