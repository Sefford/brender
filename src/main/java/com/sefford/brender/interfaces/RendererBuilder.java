package com.sefford.brender.interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sefford.brender.builder.Builder;

/**
 * Renderer Builder Interface.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public interface RendererBuilder<T extends Object> {
    /**
     * Set the type of the renderer which will be instantiated
     *
     * @param id ID of the renderer
     * @return Builder instance
     * @see com.sefford.brender.interfaces.Renderable#getRenderableId()
     */
    RendererBuilder instantiate(int id);

    /**
     * Sets the view to instatiate the Renderer layout
     *
     * @param view View to instantiate the layout
     * @return Builder
     */
    RendererBuilder into(View view);

    /**
     * Sets the Layout Inflater to instantiate the view with
     *
     * @param inflater Layout Inflater to use
     * @return Builder instance
     */
    RendererBuilder using(LayoutInflater inflater);

    /**
     * Set the parent of the group to instatiate the view in
     *
     * @param parent ViewGroup of the view
     * @return Builder
     */
    RendererBuilder inside(ViewGroup parent);

    /**
     * Set the interacting postable for capturing events
     *
     * @param extras Interacting postable to capture events
     * @return Builder instance
     */
    RendererBuilder addingConfiguratior(T extras);

    /**
     * Set the interacting postable for capturing events.
     *
     * @param postable Interacting postable to capture events
     * @return Builder instance
     */
    Builder interactingWith(Postable postable);

    /**
     * Creates an instance of the View with its related Renderer tagged inside
     *
     * @return View with the Renderer tagged
     */
    View create();
}
