package com.sefford.brender.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sefford.brender.data.CursorAdapterData
import com.sefford.brender.components.Renderable
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.RendererFactory
import com.sefford.common.interfaces.Postable

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

import org.junit.Assert.assertEquals
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by sefford on 2/08/15.
 */
@RunWith(RobolectricTestRunner::class)
class RendererCursorAdapterTest {

    lateinit var adapter: RendererCursorAdapter

    @Mock
    lateinit var data: CursorAdapterData
    @Mock
    lateinit var renderable: Renderable
    @Mock
    lateinit var factory: RendererFactory
    @Mock
    lateinit var renderer: Renderer<Renderable>
    @Mock
    lateinit var bus: Postable

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        `when`(data.getItem(EXPECTED_POSITION)).thenReturn(renderable)
        `when`(renderable.renderableId).thenReturn(EXPECTED_LAYOUT)

        adapter = spy(RendererCursorAdapter(RuntimeEnvironment.application, data, 0, factory, bus))
    }

    @Test
    @Throws(Exception::class)
    fun testNewView() {
        val container = mock(ViewGroup::class.java)
        val inflater = mock(LayoutInflater::class.java)
        val view = mock(View::class.java)
        doReturn(inflater).`when`(adapter).getInflater(RuntimeEnvironment.application)
        `when`(data.position).thenReturn(EXPECTED_POSITION)
        `when`(inflater.inflate(EXPECTED_LAYOUT, container, false)).thenReturn(view)
        `when`(factory.getRenderer(EXPECTED_LAYOUT, bus, view)).thenReturn(renderer)

        assertEquals(view, adapter.newView(RuntimeEnvironment.application, data, container))
        verify(view, times(1)).tag = renderer
    }

    @Test
    @Throws(Exception::class)
    fun testBindView() {
        val view = mock(View::class.java)
        `when`(view.tag).thenReturn(renderer)
        `when`(data.count).thenReturn(EXPECTED_COUNT)

        adapter.bindView(view, RuntimeEnvironment.application, data)

        val order = inOrder(renderer)
        order.verify<Renderer<Renderable>>(renderer, times(1)).render(renderable, EXPECTED_POSITION, java.lang.Boolean.TRUE, java.lang.Boolean.TRUE)
        order.verify<Renderer<Renderable>>(renderer, times(1)).hookUpListeners(renderable)
    }

    companion object {

        internal const val EXPECTED_POSITION = 0
        internal const val EXPECTED_LAYOUT = 0x1234
        internal const val EXPECTED_COUNT = 1
    }

}
