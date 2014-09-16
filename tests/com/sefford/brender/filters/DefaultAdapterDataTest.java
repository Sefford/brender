package com.sefford.brender.filters;

import com.sefford.brender.interfaces.Renderable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class DefaultAdapterDataTest {

    private static final int EXPECTED_POS = 2;
    @Mock
    Renderable element;

    ArrayList<Renderable> master;
    DefaultAdapterData adapterData;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        master = new ArrayList<Renderable>();
        master.add(element);
        master.add(element);
        master.add(element);

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
        assertEquals(element, adapterData.getItem(EXPECTED_POS));
    }

    @Test
    public void testGetFilter() throws Exception {
        assertThat(adapterData.getFilter(), instanceOf(NullFilter.class));
    }
}