package com.sefford.brender.builder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderer;
import com.sefford.brender.interfaces.RendererFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests the Renderer Creator Builder
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
@RunWith(RobolectricTestRunner.class)
public class BuilderTest {

    public static final int EXPECTED_RENDERABLE_ID = 1;
    public static final int EXPECTED_OTHER_RENDERABLE_ID = 2;
    private static final long EXPECTED_ACTIVE_USER_ID = 9876;
    @Mock
    private RendererFactory<Object> factory;
    @Mock
    private Renderer renderer;
    @Mock
    private Renderer otherRenderer;
    @Mock
    private View convertView;
    @Mock
    private ViewGroup parent;
    @Mock
    private LayoutInflater inflater;
    @Mock
    private Postable bus;
    @Mock
    private Object extras;
    private Builder<Object> builder;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        builder = spy(new Builder<Object>(factory));
        when(factory.getRenderer(EXPECTED_RENDERABLE_ID, bus, extras)).thenReturn(renderer);
        when(factory.getRenderer(EXPECTED_OTHER_RENDERABLE_ID, bus, extras)).thenReturn(otherRenderer);
        when(inflater.inflate(EXPECTED_RENDERABLE_ID, parent, false)).thenReturn(convertView);
        when(inflater.inflate(EXPECTED_RENDERABLE_ID, null, false)).thenReturn(convertView);
        when(convertView.getTag()).thenCallRealMethod();
        doCallRealMethod().when(convertView).setTag(renderer);
        when(renderer.getId()).thenReturn(EXPECTED_RENDERABLE_ID);
    }

    @Test
    public void testCreate() throws Exception {
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(convertView)
                .inside(parent)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras);
        assertThat((Renderer) builder.create().getTag(), equalTo(renderer));
    }

    @Test
    public void testCreateNullView() throws Exception {
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(null)
                .inside(parent)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras);
        assertThat((Renderer) builder.create().getTag(), equalTo(renderer));
    }

    @Test
    public void testCreateNullParent() throws Exception {
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(convertView)
                .inside(null)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras);
        assertThat((Renderer) builder.create().getTag(), equalTo(renderer));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateNullInflater() throws Exception {
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(convertView)
                .inside(parent)
                .using(null)
                .interactingWith(bus)
                .addingConfiguratior(extras);
        assertThat((Renderer) builder.create().getTag(), equalTo(renderer));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateInvalidID() throws Exception {
        builder.instantiate(0)
                .into(convertView)
                .inside(parent)
                .using(null)
                .interactingWith(bus)
                .addingConfiguratior(extras);
        assertThat((Renderer) builder.create().getTag(), equalTo(renderer));
    }

    @Test(expected = IllegalStateException.class)
    public void testNullFactory() throws Exception {
        builder = new Builder<Object>(null);
        builder.instantiate(0)
                .into(convertView)
                .inside(parent)
                .using(null)
                .interactingWith(bus)
                .addingConfiguratior(extras);
        assertThat((Renderer) builder.create().getTag(), equalTo(renderer));
    }

    @Test
    public void testIsRecyclableWithNullView() throws Exception {
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(null)
                .inside(parent)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras)
                .create();
        verify(inflater, times(1)).inflate(EXPECTED_RENDERABLE_ID, parent, false);
    }

    @Test
    public void testIsRecyclableWithNullTag() throws Exception {
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(convertView)
                .inside(parent)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras)
                .create();
        verify(inflater, times(1)).inflate(EXPECTED_RENDERABLE_ID, parent, false);
    }

    @Test
    public void testIsRecyclableWithDifferentIds() throws Exception {
        when(convertView.getTag()).thenReturn(otherRenderer);
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(convertView)
                .inside(parent)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras)
                .create();
        verify(inflater, times(1)).inflate(EXPECTED_RENDERABLE_ID, parent, false);
    }

    @Test
    public void testIsRecyclableWithSameIds() throws Exception {
        when(convertView.getTag()).thenReturn(renderer);
        builder.instantiate(EXPECTED_RENDERABLE_ID)
                .into(convertView)
                .inside(parent)
                .using(inflater)
                .interactingWith(bus)
                .addingConfiguratior(extras)
                .create();
        verify(convertView, atLeast(2)).getTag();
    }
}
