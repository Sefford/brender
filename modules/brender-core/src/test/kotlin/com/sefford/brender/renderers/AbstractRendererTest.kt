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
package com.sefford.brender.renderers

import android.view.View
import com.sefford.brender.components.Renderable
import com.sefford.common.interfaces.Postable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

/**
 * Tests for Abstract Renderer
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
class AbstractRendererTest {
    @Mock
    lateinit var bus: Postable
    @Mock
    lateinit var view: View

    internal lateinit var renderer: RendererTest

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        renderer = RendererTest(EXPECTED_RENDERER_ID, bus, view)
    }

    @Test
    @Throws(Exception::class)
    fun testInitialization() {
        assertEquals(EXPECTED_RENDERER_ID, renderer.id)
        assertEquals(bus, renderer.postable)
        assertEquals(view, renderer.itemView)
    }

    @Test
    @Throws(Exception::class)
    fun testGetId() {
        assertThat(renderer.id, equalTo(EXPECTED_RENDERER_ID))
    }

    internal inner class RendererTest
    /**
     * Creates a new instance of a Renderer
     *
     * @param id       Building ID of the renderer
     * @param postable Bus Manager to notify the event to the UI
     */
    (id: Int, postable: Postable, view: View) : AbstractRenderer<Renderable>(id, postable, view) {

        override fun hookUpListeners(renderable: Renderable) {
            // This space for rent
        }

        override fun render(renderable: Renderable, position: Int, first: Boolean, last: Boolean) {
            // This one too
        }

        override fun refresh(renderable: Renderable, payloads: List<*>) {

        }

        override fun clean() {
            // And this
        }
    }

    companion object {

        internal val EXPECTED_RENDERER_ID = 1234
    }
}
