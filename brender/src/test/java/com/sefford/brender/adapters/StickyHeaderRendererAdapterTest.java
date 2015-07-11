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
package com.sefford.brender.adapters;

import android.view.View;
import android.view.ViewGroup;
import com.sefford.brender.interfaces.*;
import com.sefford.common.interfaces.Postable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by sefford on 10/04/15.
 */
@RunWith(RobolectricTestRunner.class)
public class StickyHeaderRendererAdapterTest {

    static final long EXPECTED_HEADER_ID = 12345L;
    static final int EXPECTED_RENDERABLE_ID = 11111;
    static final int EXPECTED_SIZE = 1;

    StickyHeaderRendererAdapter adapter;

    @Mock
    HeaderIdentifier<Renderable> identifier;
    @Mock
    RendererFactory factory;
    @Mock
    AdapterData data;
    @Mock
    Postable bus;
    @Mock
    Renderable renderable;
    @Mock
    Renderer<Renderable> renderer;
    @Mock
    View view;
    @Mock
    ViewGroup parent;


    @Before
    public void setUp() throws Exception {
        initMocks(this);

        adapter = spy(new StickyHeaderRendererAdapter(data, factory, bus, identifier));
    }

    @Test
    public void testGetHeaderView() throws Exception {
        doReturn(view).when(adapter).configureRenderer(view, parent, EXPECTED_RENDERABLE_ID);
        doReturn(renderable).when(adapter).getItem(0);
        when(identifier.getRenderableId(renderable, 0)).thenReturn(EXPECTED_RENDERABLE_ID);
        when(view.getTag()).thenReturn(renderer);
        when(data.size()).thenReturn(EXPECTED_SIZE);
        when(data.getItem(anyInt())).thenReturn(renderable);

        adapter.getHeaderView(0, view, parent);

        InOrder order = inOrder(renderer);
        order.verify(renderer, times(1)).render(renderable, 0, Boolean.TRUE, Boolean.TRUE);
        order.verify(renderer, times(1)).hookUpListeners(renderable);
    }

    @Test
    public void testGetHeaderId() throws Exception {
        when(identifier.getHeaderId(renderable, 0)).thenReturn(EXPECTED_HEADER_ID);
        doReturn(renderable).when(adapter).getItem(0);

        assertEquals(EXPECTED_HEADER_ID, adapter.getHeaderId(0));
    }
}