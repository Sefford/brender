package com.sefford.brender;

import android.content.Context;
import android.view.View;
import com.sefford.brender.interfaces.Postable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for Abstract Renderer
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class AbstractRendererTest {

    static final int EXPECTED_RENDERER_ID = 1234;
    @Mock
    Postable bus;
    RendererTest renderer;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        renderer = new RendererTest(EXPECTED_RENDERER_ID, bus);
    }

    @Test
    public void testInitialization() throws Exception {
        assertThat(renderer.id, equalTo(EXPECTED_RENDERER_ID));
        assertThat(renderer.postable, equalTo(bus));
    }

    @Test
    public void testGetId() throws Exception {
        assertThat(renderer.getId(), equalTo(EXPECTED_RENDERER_ID));
    }

    class RendererTest extends AbstractRenderer<Object> {

        /**
         * Creates a new instance of a Renderer
         *
         * @param id       Building ID of the renderer
         * @param postable Bus Manager to notify the event to the UI
         */
        public RendererTest(int id, Postable postable) {
            super(id, postable);
        }

        @Override
        public void mapViews(View view) {
            // Do nothing
        }

        @Override
        public void hookUpListeners(View view, Object renderable) {
            // This space for rent
        }

        @Override
        public void render(Context context, Object renderable, int position, boolean first, boolean last) {
            // This one too
        }
    }
}
