package com.sefford.brender.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class NullFilterTest {

    private NullFilter filter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        filter = new NullFilter();
    }

    @Test
    public void testPerformFiltering() throws Exception {
        assertNull(filter.performFiltering(""));
    }

    @Test
    public void testPublishResults() throws Exception {
        filter.publishResults(null, null);
    }
}