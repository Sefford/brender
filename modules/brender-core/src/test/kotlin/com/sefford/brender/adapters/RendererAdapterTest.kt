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


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import com.sefford.brender.components.AdapterData
import com.sefford.brender.components.Renderable
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.RendererFactory
import com.sefford.brender.filters.NullFilter
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
 * Renderer Adapter Tests
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
@RunWith(RobolectricTestRunner::class)
class RendererAdapterTest {

    lateinit var adapter: RendererAdapter

    @Mock
    lateinit var postable: Postable
    @Mock
    lateinit var factory: RendererFactory
    @Mock
    lateinit var renderer: Renderer<*>
    @Mock
    lateinit var view: View
    @Mock
    lateinit var parent: ViewGroup
    @Mock
    internal lateinit var renderable: RenderableTest
    @Mock
    lateinit var extras: Any
    @Mock
    lateinit var data: AdapterData
    @Mock
    lateinit var filter: Filter
    @Mock
    lateinit var inflater: LayoutInflater

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        `when`(inflater.inflate(EXPECTED_RENDERABLE_ID, parent, java.lang.Boolean.FALSE)).thenReturn(view)
        `when`(view.tag).thenReturn(renderer)
        `when`(parent.context).thenReturn(RuntimeEnvironment.application)

        `when`(data.size()).thenReturn(EXPECTED_COUNT)
        `when`(data.getItem(anyInt())).thenReturn(renderable)
        `when`(data.getItemId(anyInt())).thenReturn(EXPECTED_ITEM_ID)
        `when`(data.filter).thenReturn(filter)

        `when`(renderable.renderableId).thenReturn(EXPECTED_RENDERABLE_ID)
        `when`(renderer.id).thenReturn(EXPECTED_RENDERABLE_ID)

        adapter = spy(RendererAdapter(data, factory, postable))
        doReturn(inflater).`when`(adapter).createInflater(RuntimeEnvironment.application)
    }

    @Test
    @Throws(Exception::class)
    fun testGetCount() {
        assertEquals(EXPECTED_COUNT.toLong(), adapter.count.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetItem() {
        assertEquals(renderable, adapter.getItem(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemId() {
        assertEquals(EXPECTED_ITEM_ID, adapter.getItemId(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetViewOnlyOne() {
        adapter.getView(0, view, parent)
        verify(factory, times(1)).getRenderer(EXPECTED_RENDERABLE_ID, postable, view)
        verify(renderer as Renderer<Any>, times(1)).hookUpListeners(renderable)
        verify(renderer as Renderer<Any>, times(1)).render(renderable, 0, true, true)
    }

    @Test
    @Throws(Exception::class)
    fun testGetViewNotTheOnlyOne() {
        adapter.getView(3, view, parent)
        verify(factory, times(1)).getRenderer(EXPECTED_RENDERABLE_ID, postable, view)
        verify(renderer  as Renderer<Any>, times(1)).hookUpListeners(renderable)
        verify(renderer as Renderer<Any>, times(1)).render(renderable, 3, false, false)
    }

    @Test
    @Throws(Exception::class)
    fun testGetFilter() {
        assertNotEquals(NullFilter::class.java.canonicalName, adapter.filter.javaClass.canonicalName)
    }

    @Test
    @Throws(Exception::class)
    fun testGetFilterDataHasNoFilterConfigured() {
        `when`(data.filter).thenReturn(null)

        assertEquals(NullFilter::class.java.canonicalName, adapter.filter.javaClass.canonicalName)
    }

    @Test
    @Throws(Exception::class)
    fun testIsAdapterIntialized() {
        assertTrue(adapter.isAdapterInitialized(EXPECTED_RENDERABLE_ID))
    }

    @Test
    @Throws(Exception::class)
    fun testIsAdapterNotIntializedIncorrectId() {
        assertFalse(adapter.isAdapterInitialized(Integer.MIN_VALUE))
    }

    @Test
    @Throws(Exception::class)
    fun testCreateInflater() {
        doCallRealMethod().`when`(adapter).createInflater(RuntimeEnvironment.application)

        assertNotNull(adapter.createInflater(RuntimeEnvironment.application))
    }

    @Test
    @Throws(Exception::class)
    fun testIsRecyclableWithNullView() {
        assertFalse(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, null))
    }

    @Test
    @Throws(Exception::class)
    fun testIsRecyclableWithNullTag() {
        `when`(view.tag).thenReturn(null)

        assertFalse(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, view))
    }

    @Test
    @Throws(Exception::class)
    fun testIsRecyclableWithDifferentIds() {
        `when`(renderer.id).thenReturn(EXPECTED_RENDERABLE_ID + 10000)

        assertFalse(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, view))
    }

    @Test
    @Throws(Exception::class)
    fun testIsRecyclableWithSameIds() {
        assertTrue(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, view))
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun testConfigureRendererInvalidID() {
        doReturn(java.lang.Boolean.FALSE).`when`(adapter).isAdapterInitialized(0)

        adapter.configureRenderer(view, parent, 0)
    }

    @Test
    @Throws(Exception::class)
    fun testCreate() {
        assertEquals(renderer, adapter.configureRenderer(view, parent, EXPECTED_RENDERABLE_ID).tag)
    }

    @Test
    @Throws(Exception::class)
    fun testGetViewTypeCount() {
        `when`(data.viewTypeCount).thenReturn(EXPECTED_VIEWTYPE_COUNT)

        assertEquals(EXPECTED_VIEWTYPE_COUNT.toLong(), adapter.viewTypeCount.toLong())
    }

    @Test
    fun testGetViewTypeCountWithNoData() {
        `when`(data.viewTypeCount).thenReturn(0)

        assertEquals(EXPECTED_DEFAULT_VIEWTYPE_COUNT.toLong(), adapter.viewTypeCount.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testNotifyDataSetChanged() {
        adapter.notifyDataSetChanged()

        verify<AdapterData>(data, times(1)).notifyDataSetChanged()
    }

    @Test
    @Throws(Exception::class)
    fun testNotifyDataSetInvalidated() {
        adapter.notifyDataSetInvalidated()

        verify<AdapterData>(data, times(1)).notifyDataSetChanged()
    }

    internal inner class RenderableTest : Renderable {

        override val renderableId: Int
            get() = 0
    }

    companion object {

        internal const val EXPECTED_COUNT = 1
        internal const val EXPECTED_ITEM_ID = 921984L
        internal const val EXPECTED_RENDERABLE_ID = EXPECTED_ITEM_ID.toInt()
        internal const val EXPECTED_VIEWTYPE_COUNT = 3
        internal const val EXPECTED_DEFAULT_VIEWTYPE_COUNT = 1
    }
}
