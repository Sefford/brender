package com.sefford.brender.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefford.brender.data.CursorAdapterData;
import com.sefford.brender.interfaces.Renderable;
import com.sefford.brender.interfaces.Renderer;
import com.sefford.brender.interfaces.RendererFactory;
import com.sefford.common.interfaces.Postable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by sefford on 2/08/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class RendererCursorAdapterTest {

    static final int EXPECTED_POSITION = 0;
    static final int EXPECTED_LAYOUT = 0x1234;
    public static final int EXPECTED_COUNT = 1;

    RendererCursorAdapter adapter;

    @Mock
    CursorAdapterData data;
    @Mock
    Renderable renderable;
    @Mock
    RendererFactory factory;
    @Mock
    Renderer<Renderable> renderer;
    @Mock
    Postable bus;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(data.getItem(EXPECTED_POSITION)).thenReturn(renderable);
        when(renderable.getRenderableId()).thenReturn(EXPECTED_LAYOUT);

        adapter = spy(new RendererCursorAdapter(RuntimeEnvironment.application, data, 0, factory, bus));
    }

    @Test
    public void testNewView() throws Exception {
        final ViewGroup container = mock(ViewGroup.class);
        final LayoutInflater inflater = mock(LayoutInflater.class);
        final View view = mock(View.class);
        doReturn(inflater).when(adapter).getInflater(RuntimeEnvironment.application);
        when(data.getPosition()).thenReturn(EXPECTED_POSITION);
        when(inflater.inflate(EXPECTED_LAYOUT, container, false)).thenReturn(view);
        when(factory.getRenderer(EXPECTED_LAYOUT, bus, view)).thenReturn(renderer);

        assertEquals(view, adapter.newView(RuntimeEnvironment.application, data, container));
        verify(view, times(1)).setTag(renderer);
    }

    @Test
    public void testBindView() throws Exception {
        final View view = mock(View.class);
        when(view.getTag()).thenReturn(renderer);
        when(data.getCount()).thenReturn(EXPECTED_COUNT);

        adapter.bindView(view, RuntimeEnvironment.application, data);

        InOrder order = inOrder(renderer);
        order.verify(renderer, times(1)).render(renderable, EXPECTED_POSITION, Boolean.TRUE, Boolean.TRUE);
        order.verify(renderer, times(1)).hookUpListeners(renderable);
    }

}