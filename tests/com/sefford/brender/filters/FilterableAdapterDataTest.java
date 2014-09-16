package com.sefford.brender.filters;

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
}