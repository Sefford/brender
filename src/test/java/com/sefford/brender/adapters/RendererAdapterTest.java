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


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Renderer Adapter Tests
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
@RunWith(RobolectricTestRunner.class)
public class RendererAdapterTest {

    static final int EXPECTED_COUNT = 1;
    static final long EXPECTED_ITEM_ID = 921984L;
    static final int EXPECTED_RENDERABLE_ID = (int) EXPECTED_ITEM_ID;
    static final int EXPECTED_VIEWTYPE_COUNT = 3;

    RendererAdapter adapter;

    @Mock
    Postable postable;
    @Mock
    RendererFactory factory;
    @Mock
    Renderer renderer;
    @Mock
    View view;
    @Mock
    ViewGroup parent;
    @Mock
    RenderableTest renderable;
    @Mock
    Object extras;
    @Mock
    AdapterData data;
    @Mock
    Filter filter;
    @Mock
    LayoutInflater inflater;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(inflater.inflate(EXPECTED_RENDERABLE_ID, parent, Boolean.FALSE)).thenReturn(view);
        when(view.getTag()).thenReturn(renderer);
        when(parent.getContext()).thenReturn(RuntimeEnvironment.application);

        when(data.size()).thenReturn(EXPECTED_COUNT);
        when(data.getItem(anyInt())).thenReturn(renderable);
        when(data.getItemId(anyInt())).thenReturn(EXPECTED_ITEM_ID);
        when(data.getFilter()).thenReturn(filter);

        when(renderable.getRenderableId()).thenReturn(EXPECTED_RENDERABLE_ID);
        when(renderer.getId()).thenReturn(EXPECTED_RENDERABLE_ID);

        adapter = spy(new RendererAdapter(data, factory, postable));
        doReturn(inflater).when(adapter).createInflater(RuntimeEnvironment.application);
    }

    @Test
    public void testGetCount() throws Exception {
        assertEquals(EXPECTED_COUNT, adapter.getCount());
    }

    @Test
    public void testGetItem() throws Exception {
        assertEquals(renderable, adapter.getItem(0));
    }

    @Test
    public void testGetItemId() throws Exception {
        assertEquals(EXPECTED_ITEM_ID, adapter.getItemId(0));
    }

    @Test
    public void testGetViewOnlyOne() throws Exception {
        adapter.getView(0, null, parent);
        verify(factory, times(1)).getRenderer(EXPECTED_RENDERABLE_ID, postable, null);
        verify(renderer, times(1)).hookUpListeners(renderable);
        verify(renderer, times(1)).render(renderable, 0, true, true);
    }

    @Test
    public void testGetViewNotTheOnlyOne() throws Exception {
        adapter.getView(3, null, parent);
        verify(factory, times(1)).getRenderer(EXPECTED_RENDERABLE_ID, postable, null);
        verify(renderer, times(1)).hookUpListeners(renderable);
        verify(renderer, times(1)).render(renderable, 3, false, false);
    }

    @Test
    public void testGetFilter() throws Exception {
        assertNotEquals(NullFilter.class.getCanonicalName(), adapter.getFilter().getClass().getCanonicalName());
    }

    @Test
    public void testGetFilterDataHasNoFilterConfigured() throws Exception {
        when(data.getFilter()).thenReturn(null);

        assertEquals(NullFilter.class.getCanonicalName(), adapter.getFilter().getClass().getCanonicalName());
    }

    @Test
    public void testIsAdapterIntialized() throws Exception {
        assertTrue(adapter.isAdapterInitialized(EXPECTED_RENDERABLE_ID));
    }

    @Test
    public void testIsAdapterNotIntializedMissingFactory() throws Exception {
        adapter = new RendererAdapter(data, null, postable);

        assertFalse(adapter.isAdapterInitialized(EXPECTED_RENDERABLE_ID));
    }

    @Test
    public void testIsAdapterNotIntializedIncorrectId() throws Exception {
        assertFalse(adapter.isAdapterInitialized(Integer.MIN_VALUE));
    }

    @Test
    public void testCreateInflater() throws Exception {
        doCallRealMethod().when(adapter).createInflater(RuntimeEnvironment.application);

        assertNotNull(adapter.createInflater(RuntimeEnvironment.application));
    }

    @Test
    public void testIsRecyclableWithNullView() throws Exception {
        assertFalse(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, null));
    }

    @Test
    public void testIsRecyclableWithNullTag() throws Exception {
        when(view.getTag()).thenReturn(null);

        assertFalse(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, view));
    }

    @Test
    public void testIsRecyclableWithDifferentIds() throws Exception {
        when(renderer.getId()).thenReturn(EXPECTED_RENDERABLE_ID + 10000);

        assertFalse(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, view));
    }

    @Test
    public void testIsRecyclableWithSameIds() throws Exception {
        assertTrue(adapter.isRecyclable(EXPECTED_RENDERABLE_ID, view));
    }

    @Test(expected = IllegalStateException.class)
    public void testConfigureRendererInvalidID() throws Exception {
        doReturn(Boolean.FALSE).when(adapter).isAdapterInitialized(0);

        adapter.configureRenderer(view, parent, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testConfigureRendererWithNullFactory() throws Exception {
        adapter = new RendererAdapter(data, null, postable);

        adapter.configureRenderer(view, parent, EXPECTED_RENDERABLE_ID);
    }

    @Test
    public void testCreate() throws Exception {
        assertEquals(renderer, adapter.configureRenderer(view, parent, EXPECTED_RENDERABLE_ID).getTag());
    }

    @Test
    public void testCreateNullView() throws Exception {
        assertEquals(renderer, adapter.configureRenderer(null, parent, EXPECTED_RENDERABLE_ID).getTag());
    }

    @Test
    public void testGetViewTypeCount() throws Exception {
        when(data.getViewTypeCount()).thenReturn(EXPECTED_VIEWTYPE_COUNT);

        assertEquals(EXPECTED_VIEWTYPE_COUNT, adapter.getViewTypeCount());

    }

    @Test
    public void testNotifyDataSetChanged() throws Exception {
        adapter.notifyDataSetChanged();

        verify(data, times(1)).notifyDataSetChanged();
    }

    @Test
    public void testNotifyDataSetInvalidated() throws Exception {
        adapter.notifyDataSetInvalidated();

        verify(data, times(1)).notifyDataSetChanged();
    }

    class RenderableTest implements Renderable {

        @Override
        public int getRenderableId() {
            return 0;
        }
    }
}
