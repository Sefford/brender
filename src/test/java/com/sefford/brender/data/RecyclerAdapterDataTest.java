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

import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.Renderable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by sefford on 9/04/15.
 */
@RunWith(RobolectricTestRunner.class)
public class RecyclerAdapterDataTest {

    static final int EXPECTED_RENDERABLE_ID = 0x12345678;
    static final int EXPECTED_SIZE = 1;

    RecyclerAdapterData adapterData;

    @Mock
    Renderable renderable;

    List<Renderable> renderableList;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(renderable.getRenderableId()).thenReturn(EXPECTED_RENDERABLE_ID);

        renderableList = new ArrayList<>();
        renderableList.add(renderable);

        adapterData = new RecyclerAdapterData(renderableList);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(EXPECTED_SIZE, adapterData.size());
    }

    @Test
    public void testGetItemId() throws Exception {
        assertEquals(EXPECTED_RENDERABLE_ID, adapterData.getItemId(0));
    }

    @Test
    public void testGetItem() throws Exception {
        assertEquals(renderable, adapterData.getItem(0));
    }

    @Test
    public void testGetFilter() throws Exception {
        assertEquals(NullFilter.class.getCanonicalName(), adapterData.getFilter().getClass().getCanonicalName());
    }
}