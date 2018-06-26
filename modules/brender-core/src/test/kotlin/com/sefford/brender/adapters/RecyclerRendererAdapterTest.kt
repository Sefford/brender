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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.sefford.brender.components.Renderable
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.RendererFactory
import com.sefford.brender.data.RecyclerAdapterData
import com.sefford.common.interfaces.Postable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by sefford on 9/04/15.
 */
@RunWith(RobolectricTestRunner::class)
class RecyclerRendererAdapterTest {

    lateinit var adapter: RecyclerRendererAdapter

    @Mock
    lateinit var data: RecyclerAdapterData
    @Mock
    lateinit var factory: RendererFactory
    @Mock
    lateinit var postable: Postable
    @Mock
    lateinit var parent: ViewGroup
    @Mock
    lateinit var inflater: LayoutInflater
    @Mock
    lateinit var view: View
    @Mock
    internal lateinit var renderer: TestRenderer
    @Mock
    lateinit var renderable: Renderable

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        whenever(parent.context).thenReturn(RuntimeEnvironment.application)
        whenever(inflater.inflate(EXPECTED_LAYOUT_ID, parent, java.lang.Boolean.FALSE)).thenReturn(view)
        whenever(data.getItem(0)).thenReturn(renderable)
        whenever(data.getItemId(0)).thenReturn(java.lang.Long.valueOf(EXPECTED_LAYOUT_ID.toLong()))
        whenever(data.size()).thenReturn(EXPECTED_COUNT)
        whenever(renderable.renderableId).thenReturn(EXPECTED_LAYOUT_ID)

        adapter = spy(RecyclerRendererAdapter(data, factory, postable))

        doReturn(inflater).`when`(adapter).getInflater(RuntimeEnvironment.application)
    }

    @Test
    @Throws(Exception::class)
    fun testOnCreateViewHolder() {
        whenever(factory.getRenderer(EXPECTED_LAYOUT_ID, postable, view)).thenReturn(mock<TestRenderer>())
        adapter.onCreateViewHolder(parent, EXPECTED_LAYOUT_ID)

        verify<RendererFactory>(factory, times(1)).getRenderer(EXPECTED_LAYOUT_ID, postable, view)
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemCount() {
        assertEquals(EXPECTED_COUNT.toLong(), adapter.itemCount.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemViewType() {
        assertEquals(EXPECTED_LAYOUT_ID.toLong(), adapter.getItemViewType(0).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetInflater() {
        doCallRealMethod().`when`(adapter).getInflater(RuntimeEnvironment.application)

        assertNotNull(adapter.getInflater(RuntimeEnvironment.application))
    }

    @Test
    fun testOnWindowAttached() {
        adapter.onViewAttachedToWindow(renderer)

        val order = inOrder(renderer)
        order.verify<TestRenderer>(renderer, times(1)).render(renderable, 0, java.lang.Boolean.TRUE, java.lang.Boolean.TRUE)
        order.verify<TestRenderer>(renderer, times(1)).hookUpListeners(renderable)
    }

    @Test
    fun testOnWindowDetached() {
        adapter.onViewDetachedFromWindow(renderer)

        verify<TestRenderer>(renderer, times(1)).clean()
    }

    @Test
    fun testAdapterIsEmptyWithEmptyData() {
        `when`(data.size()).thenReturn(0)

        assertTrue(adapter.isEmpty)
    }

    @Test
    fun testAdapterIsEmptyWithNonEmptyData() {
        `when`(data.size()).thenReturn(EXPECTED_COUNT)

        assertFalse(adapter.isEmpty)
    }

    internal inner class TestRenderer(itemView: View) : RecyclerView.ViewHolder(itemView), Renderer<Renderable> {

        override val id: Int
            get() = 0

        override fun hookUpListeners(renderable: Renderable) {

        }

        override fun render(renderable: Renderable, position: Int, first: Boolean, last: Boolean) {

        }

        override fun refresh(renderable: Renderable, payloads: List<*>) {

        }

        override fun clean() {

        }
    }

    companion object {

        internal val EXPECTED_LAYOUT_ID = -0x789abcdf
        internal val EXPECTED_COUNT = 1
    }
}
