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
package com.sefford.brender.data;

import com.sefford.brender.data.DefaultAdapterData;
import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.Renderable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class DefaultAdapterDataTest {

    static final int EXPECTED_POS = 2;
    static final int EXPECTED_VIEWTYPE_COUNT = 3;
    static final int EXPECTED_RENDERABLE_ID = 991984;

    @Mock
    Renderable renderable;

    ArrayList<Renderable> master;

    DefaultAdapterData adapterData;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        master = new ArrayList<>();
        master.add(renderable);
        master.add(renderable);
        master.add(renderable);

        adapterData = new DefaultAdapterData(master);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(master.size(), adapterData.size());
    }

    @Test
    public void testGetItemId() throws Exception {
        assertEquals(Long.valueOf((long) EXPECTED_POS).longValue(), adapterData.getItemId(EXPECTED_POS));
    }

    @Test
    public void testGetItem() throws Exception {
        assertEquals(renderable, adapterData.getItem(EXPECTED_POS));
    }

    @Test
    public void testGetFilter() throws Exception {
        assertThat(adapterData.getFilter(), instanceOf(NullFilter.class));
    }


    @Test
    public void testComputeViewTypes() throws Exception {
        when(renderable.getRenderableId()).thenReturn(EXPECTED_RENDERABLE_ID)
                .thenReturn(EXPECTED_RENDERABLE_ID + 1000)
                .thenReturn(EXPECTED_RENDERABLE_ID + 2000);
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(renderable);
        renderables.add(renderable);
        renderables.add(renderable);
        adapterData.viewTypes.clear();

        adapterData.computeViewTypes(renderables);

        assertEquals(EXPECTED_VIEWTYPE_COUNT, adapterData.viewTypes.size());
    }

    @Test
    public void testGetViewTypeCount() throws Exception {
        when(renderable.getRenderableId()).thenReturn(EXPECTED_RENDERABLE_ID)
                .thenReturn(EXPECTED_RENDERABLE_ID + 1000)
                .thenReturn(EXPECTED_RENDERABLE_ID + 2000);
        final List<Renderable> renderables = new ArrayList<>();
        renderables.add(renderable);
        renderables.add(renderable);
        renderables.add(renderable);

        adapterData = new DefaultAdapterData(renderables);

        assertEquals(EXPECTED_VIEWTYPE_COUNT, adapterData.getViewTypeCount());
    }

    @Test
    public void testNotifyDataSetChanged() throws Exception {
        master.clear();
        when(renderable.getRenderableId()).thenReturn(EXPECTED_RENDERABLE_ID)
                .thenReturn(EXPECTED_RENDERABLE_ID + 1000)
                .thenReturn(EXPECTED_RENDERABLE_ID + 2000);
        master.add(renderable);
        master.add(renderable);
        master.add(renderable);

        adapterData.notifyDataSetChanged();

        assertEquals(EXPECTED_VIEWTYPE_COUNT, adapterData.getViewTypeCount());
    }
}