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
package com.sefford.brender.renderers;

import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for Abstract Renderer
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class AbstractRendererTest {

    static final int EXPECTED_RENDERER_ID = 1234;
    @Mock
    Postable bus;
    RendererTest renderer;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        renderer = new RendererTest(EXPECTED_RENDERER_ID, bus);
    }

    @Test
    public void testInitialization() throws Exception {
        assertThat(renderer.id, equalTo(EXPECTED_RENDERER_ID));
        assertThat(renderer.postable, equalTo(bus));
    }

    @Test
    public void testGetId() throws Exception {
        assertThat(renderer.getId(), equalTo(EXPECTED_RENDERER_ID));
    }

    class RendererTest extends AbstractRenderer<Renderable> {

        /**
         * Creates a new instance of a Renderer
         *
         * @param id       Building ID of the renderer
         * @param postable Bus Manager to notify the event to the UI
         */
        public RendererTest(int id, Postable postable) {
            super(id, postable);
        }

        @Override
        public void hookUpListeners(Renderable renderable) {
            // This space for rent
        }

        @Override
        public void render(Renderable renderable, int position, boolean first, boolean last) {
            // This one too
        }
    }
}
