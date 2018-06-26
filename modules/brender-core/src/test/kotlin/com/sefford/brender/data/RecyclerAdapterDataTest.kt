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

import com.sefford.brender.filters.NullFilter
import com.sefford.brender.components.Renderable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

import java.util.ArrayList

import org.junit.Assert.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by sefford on 9/04/15.
 */
@RunWith(RobolectricTestRunner::class)
class RecyclerAdapterDataTest {

    lateinit var adapterData: RecyclerAdapterData

    @Mock
    lateinit var renderable: Renderable

    lateinit var renderableList: MutableList<Renderable>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        `when`(renderable.renderableId).thenReturn(EXPECTED_RENDERABLE_ID)

        renderableList = ArrayList()
        renderableList.add(renderable)

        adapterData = RecyclerAdapterData(renderableList)
    }

    @Test
    @Throws(Exception::class)
    fun testSize() {
        assertEquals(EXPECTED_SIZE.toLong(), adapterData.size().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemId() {
        assertEquals(EXPECTED_RENDERABLE_ID.toLong(), adapterData.getItemId(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetItem() {
        assertEquals(renderable, adapterData.getItem(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetFilter() {
        assertEquals(NullFilter::class.java.canonicalName, adapterData.filter.javaClass.canonicalName)
    }

    companion object {

        internal const val EXPECTED_RENDERABLE_ID = 0x12345678
        internal const val EXPECTED_SIZE = 1
    }
}
