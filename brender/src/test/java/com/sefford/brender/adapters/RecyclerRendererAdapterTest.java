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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sefford.brender.data.RecyclerAdapterData;
import com.sefford.brender.interfaces.Renderable;
import com.sefford.brender.interfaces.Renderer;
import com.sefford.brender.interfaces.RendererFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import sefford.com.common.interfaces.Postable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by sefford on 9/04/15.
 */
@RunWith(RobolectricTestRunner.class)
public class RecyclerRendererAdapterTest {

    static final int EXPECTED_LAYOUT_ID = 0x87654321;
    static final int EXPECTED_COUNT = 1;

    RecyclerRendererAdapter adapter;

    @Mock
    RecyclerAdapterData data;
    @Mock
    RendererFactory factory;
    @Mock
    Postable postable;
    @Mock
    ViewGroup parent;
    @Mock
    LayoutInflater inflater;
    @Mock
    View view;
    @Mock
    TestRenderer renderer;
    @Mock
    Renderable renderable;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(parent.getContext()).thenReturn(RuntimeEnvironment.application);
        when(inflater.inflate(EXPECTED_LAYOUT_ID, parent, Boolean.FALSE)).thenReturn(view);
        when(data.getItem(0)).thenReturn(renderable);
        when(data.getItemId(0)).thenReturn(Long.valueOf(EXPECTED_LAYOUT_ID));
        when(data.size()).thenReturn(EXPECTED_COUNT);
        when(renderable.getRenderableId()).thenReturn(EXPECTED_LAYOUT_ID);

        adapter = spy(new RecyclerRendererAdapter(data, factory, postable));

        doReturn(inflater).when(adapter).getInflater(RuntimeEnvironment.application);
    }

    @Test
    public void testOnCreateViewHolder() throws Exception {
        adapter.onCreateViewHolder(parent, EXPECTED_LAYOUT_ID);

        verify(factory, times(1)).getRenderer(EXPECTED_LAYOUT_ID, postable, view);
    }

    @Test
    public void testOnBindViewHolder() throws Exception {
        adapter.onBindViewHolder(renderer, 0);

        InOrder order = inOrder(renderer);
        order.verify(renderer, times(1)).render(renderable, 0, Boolean.TRUE, Boolean.TRUE);
        order.verify(renderer, times(1)).hookUpListeners(renderable);
    }

    @Test
    public void testGetItemCount() throws Exception {
        assertEquals(EXPECTED_COUNT, adapter.getItemCount());
    }

    @Test
    public void testGetItemViewType() throws Exception {
        assertEquals(EXPECTED_LAYOUT_ID, adapter.getItemViewType(0));
    }

    @Test
    public void testGetInflater() throws Exception {
        doCallRealMethod().when(adapter).getInflater(RuntimeEnvironment.application);

        assertNotNull(adapter.getInflater(RuntimeEnvironment.application));
    }

    private class TestRenderer extends RecyclerView.ViewHolder implements Renderer<Renderable> {
        public TestRenderer(View itemView) {
            super(itemView);
        }

        @Override
        public void hookUpListeners(Renderable renderable) {

        }

        @Override
        public void render(Renderable renderable, int position, boolean first, boolean last) {

        }

        @Override
        public int getId() {
            return 0;
        }
    }
}