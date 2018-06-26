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
import com.sefford.brender.filters.NullFilter
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class DefaultAdapterDataTest {

    @Mock
    lateinit var renderable: Renderable

    lateinit var master: MutableList<Renderable>

    lateinit var adapterData: DefaultAdapterData

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        master = mutableListOf(renderable, renderable, renderable)

        adapterData = DefaultAdapterData(master)
    }

    @Test
    @Throws(Exception::class)
    fun testSize() {
        assertEquals(master.size.toLong(), adapterData.size().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemId() {
        assertEquals(java.lang.Long.valueOf(EXPECTED_POS.toLong()).toLong(), adapterData.getItemId(EXPECTED_POS))
    }

    @Test
    @Throws(Exception::class)
    fun testGetItem() {
        assertEquals(renderable, adapterData.getItem(EXPECTED_POS))
    }

    @Test
    @Throws(Exception::class)
    fun testGetFilter() {
        assertThat<Filter>(adapterData.filter, instanceOf<Any>(NullFilter::class.java))
    }


    @Test
    @Throws(Exception::class)
    fun testComputeViewTypes() {
        `when`(renderable.renderableId).thenReturn(EXPECTED_RENDERABLE_ID)
                .thenReturn(EXPECTED_RENDERABLE_ID + 1000)
                .thenReturn(EXPECTED_RENDERABLE_ID + 2000)
        val renderables = ArrayList<Renderable>()
        renderables.add(renderable)
        renderables.add(renderable)
        renderables.add(renderable)
        adapterData.viewTypes.clear()

        adapterData.computeViewTypes(renderables)

        assertEquals(EXPECTED_VIEWTYPE_COUNT.toLong(), adapterData.viewTypes.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetViewTypeCount() {
        `when`(renderable.renderableId).thenReturn(EXPECTED_RENDERABLE_ID)
                .thenReturn(EXPECTED_RENDERABLE_ID + 1000)
                .thenReturn(EXPECTED_RENDERABLE_ID + 2000)
        val renderables = ArrayList<Renderable>()
        renderables.add(renderable)
        renderables.add(renderable)
        renderables.add(renderable)

        adapterData = DefaultAdapterData(renderables)

        assertEquals(EXPECTED_VIEWTYPE_COUNT.toLong(), adapterData.viewTypeCount.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testNotifyDataSetChanged() {
        master = mutableListOf()
        `when`(renderable.renderableId).thenReturn(EXPECTED_RENDERABLE_ID)
                .thenReturn(EXPECTED_RENDERABLE_ID + 1000)
                .thenReturn(EXPECTED_RENDERABLE_ID + 2000)
        master.add(renderable)
        master.add(renderable)
        master.add(renderable)

        adapterData.notifyDataSetChanged()

        assertEquals(EXPECTED_VIEWTYPE_COUNT.toLong(), adapterData.viewTypeCount.toLong())
    }

    companion object {

        internal val EXPECTED_POS = 2
        internal val EXPECTED_VIEWTYPE_COUNT = 3
        internal val EXPECTED_RENDERABLE_ID = 991984
    }
}
