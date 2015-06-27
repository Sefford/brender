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

import com.sefford.brender.interfaces.Renderable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class FilterableAdapterDataTest {

    private static final int EXPECTED_POS = 1;
    @Mock
    Renderable element;

    ArrayList<Renderable> master;
    ArrayList<Renderable> filtered;

    private FilterableAdapterData adapterData;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        master = new ArrayList<Renderable>();
        master.add(element);
        master.add(element);
        master.add(element);

        filtered = new ArrayList<Renderable>();
        filtered.add(element);

        adapterData = new FilterableAdapterData(master) {
            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public void notifyDataSetChanged() {

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }
        };
        adapterData.filter("");
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(master.size(), adapterData.size());
    }

    @Test
    public void testGetItemId() throws Exception {
        assertEquals(EXPECTED_POS, adapterData.getItemId(EXPECTED_POS));
    }

    @Test
    public void testGetItem() throws Exception {
        assertEquals(element, adapterData.getItem(EXPECTED_POS));
    }

    @Test
    public void testGetFilter() throws Exception {
        assertEquals(adapterData, adapterData.getFilter());
    }
}