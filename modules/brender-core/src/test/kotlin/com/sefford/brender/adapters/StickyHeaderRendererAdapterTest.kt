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
package com.sefford.brender.adapters

import android.view.View
import android.view.ViewGroup

import com.sefford.brender.components.HeaderIdentifier
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.*
import com.sefford.common.interfaces.Postable

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

import org.junit.Assert.assertEquals
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by sefford on 10/04/15.
 */
@RunWith(RobolectricTestRunner::class)
class StickyHeaderRendererAdapterTest {

    internal var adapter: StickyHeaderRendererAdapter

    @Mock
    internal var identifier: HeaderIdentifier<Renderable>? = null
    @Mock
    internal var factory: RendererFactory? = null
    @Mock
    internal var data: AdapterData? = null
    @Mock
    internal var bus: Postable? = null
    @Mock
    internal var renderable: Renderable? = null
    @Mock
    internal var renderer: Renderer<Renderable>? = null
    @Mock
    internal var view: View? = null
    @Mock
    internal var parent: ViewGroup? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        adapter = spy<StickyHeaderRendererAdapter>(StickyHeaderRendererAdapter(data, factory, bus, identifier))
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeaderView() {
        doReturn(view).`when`<StickyHeaderRendererAdapter>(adapter).configureRenderer(view, parent, EXPECTED_RENDERABLE_ID)
        doReturn(renderable).`when`<StickyHeaderRendererAdapter>(adapter).getItem(0)
        `when`(identifier!!.getRenderableId(renderable!!, 0)).thenReturn(EXPECTED_RENDERABLE_ID)
        `when`(view!!.tag).thenReturn(renderer)
        `when`(data!!.size()).thenReturn(EXPECTED_SIZE)
        `when`(data!!.getItem(ArgumentMatchers.anyInt())).thenReturn(renderable)

        adapter.getHeaderView(0, view, parent)

        val order = inOrder(renderer)
        order.verify<Renderer<Renderable>>(renderer, times(1)).render(renderable, 0, java.lang.Boolean.TRUE, java.lang.Boolean.TRUE)
        order.verify<Renderer<Renderable>>(renderer, times(1)).hookUpListeners(renderable)
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeaderId() {
        `when`(identifier!!.getHeaderId(renderable!!, 0)).thenReturn(EXPECTED_HEADER_ID)
        doReturn(renderable).`when`<StickyHeaderRendererAdapter>(adapter).getItem(0)

        assertEquals(EXPECTED_HEADER_ID, adapter.getHeaderId(0))
    }

    companion object {

        internal val EXPECTED_HEADER_ID = 12345L
        internal val EXPECTED_RENDERABLE_ID = 11111
        internal val EXPECTED_SIZE = 1
    }
}