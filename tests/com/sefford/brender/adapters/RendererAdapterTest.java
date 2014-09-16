package com.sefford.brender.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefford.brender.builder.Builder;
import com.sefford.brender.filters.DefaultAdapterData;
import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderable;
import com.sefford.brender.interfaces.Renderer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Renderer Adapter Tests
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
@RunWith(RobolectricTestRunner.class)
public class RendererAdapterTest {

    private static final int EXPECTED_COUNT = 3;
    @Mock
    Postable bus;
    @Mock
    Builder builder;
    @Mock
    Renderer rendererInterface;
    @Mock
    View view;
    @Mock
    RenderableTest renderable;
    @Mock
    Object extras;
    @Mock
    ViewGroup parent;

    DefaultAdapterData data;
    List<Renderable> renderableList;
    RendererAdapter adapter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(builder.instantiate(anyInt())).thenReturn(builder);
        when(builder.into((View) anyObject())).thenReturn(builder);
        when(builder.inside((ViewGroup) anyObject())).thenReturn(builder);
        when(builder.using((LayoutInflater) anyObject())).thenReturn(builder);
        when(builder.interactingWith(bus)).thenReturn(builder);
        when(builder.addingConfiguratior(extras)).thenReturn(builder);
        when(builder.create()).thenReturn(view);
        when(view.getTag()).thenReturn(rendererInterface);
        when(parent.getContext()).thenReturn(Robolectric.application);

        renderableList = new ArrayList<Renderable>();
        renderableList.add(renderable);
        renderableList.add(renderable);
        renderableList.add(renderable);
        data = new DefaultAdapterData(renderableList);
        adapter = new RendererAdapter(data, builder, bus, extras);
    }

    @Test
    public void testGetCount() throws Exception {
        assertThat(adapter.getCount(), equalTo(EXPECTED_COUNT));
    }

    @Test
    public void testGetItem() throws Exception {
        assertThat(adapter.getItem(0), equalTo((Renderable) renderable));
    }

    @Test
    public void testGetItemId() throws Exception {
        assertThat(adapter.getItemId(0), equalTo(0L));
    }

    @Test
    public void testGetViewOnlyOne() throws Exception {
        renderableList = new ArrayList<Renderable>();
        renderableList.add(renderable);
        data = new DefaultAdapterData(renderableList);
        adapter = new RendererAdapter(data, builder, bus, extras);
        adapter.getView(0, null, parent);
        verify(builder, times(1)).create();
        verify(rendererInterface, times(1)).hookUpListeners(view, renderable);
        verify(rendererInterface, times(1)).render(Robolectric.application, renderable, 0, true, true);
    }

    @Test
    public void testGetViewNotTheOnlyOne() throws Exception {
        renderableList.add(renderable);
        renderableList.add(renderable);
        renderableList.add(renderable);
        renderableList.add(renderable);
        adapter.getView(3, null, parent);
        verify(builder, times(1)).create();
        verify(rendererInterface, times(1)).hookUpListeners(view, renderable);
        verify(rendererInterface, times(1)).render(Robolectric.application, renderable, 3, false, false);
    }

    class RenderableTest implements Renderable {

        @Override
        public int getRenderableId() {
            return 0;
        }
    }
}
