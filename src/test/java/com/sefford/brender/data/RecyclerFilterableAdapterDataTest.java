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
public class RecyclerFilterableAdapterDataTest {

    private static final int EXPECTED_POS = 0;

    @Mock
    Renderable element;

    ArrayList<Renderable> master;
    ArrayList<Renderable> filtered;

    private RecyclerFilterableAdapterData adapterData;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        master = new ArrayList<>();
        master.add(element);
        master.add(element);
        master.add(element);

        filtered = new ArrayList<>();
        filtered.add(element);

        adapterData = new RecyclerFilterableAdapterData(master) {
            @Override
            public void addFooter(Renderable data) {

            }

            @Override
            public void addHeader(Renderable data) {

            }

            @Override
            public void removeFooter(Renderable footer) {

            }

            @Override
            public void removeHeader(Renderable header) {

            }

            @Override
            public int getHeaderViewCount() {
                return 0;
            }

            @Override
            public int getFooterViewCount() {
                return 0;
            }

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;            }
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