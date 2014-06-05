/*
 * Copyright (C) 2014 Saúl Díaz
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
package com.sefford.brender.builder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderer;

/**
 * Builder for Renderable classes
 *
 * @author Saul Diaz <sefford@ŋmail.com>
 */
public class Builder {

    /**
     * Renderer Factory to help with the instantiation
     */
    private RendererFactory factory;
    /**
     * ID of the Renderer to instantiate
     */
    private int id;
    /**
     * Layout inflater to user
     */
    private LayoutInflater inflater;
    /**
     * Parent where attach the view
     */
    private ViewGroup parent;
    /**
     * View where instantiate the Render
     */
    private View view;
    /**
     * BusManager to report changes
     */
    private Postable postable;

    /**
     * Instantiates a new Renderer Builder
     *
     * @param factory Renderer Factory
     */
    public Builder(RendererFactory factory) {
        this.factory = factory;
    }

    /**
     * Set the type of the renderer which will be instantiated
     *
     * @param id ID of the renderer
     * @return Builder
     */
    public Builder instantiate(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the view to instatiate the Renderer layout
     *
     * @param view View to instantiate the layout
     * @return Builder
     */
    public Builder into(View view) {
        this.view = view;
        return this;
    }

    /**
     * Sets the Layout Inflater to instantiate the view with
     *
     * @param inflater Layout Inflater to use
     * @return Builder
     */
    public Builder using(LayoutInflater inflater) {
        this.inflater = inflater;
        return this;
    }

    /**
     * Set the parent of the group to instatiate the view in
     *
     * @param parent ViewGroup of the view
     * @return Builder
     */
    public Builder inside(ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Set the interacting postable for capturing events
     *
     * @param postable Interacting postable to capture events
     * @return Builder
     */
    public Builder interactingWith(Postable postable) {
        this.postable = postable;
        return this;
    }

    /**
     * Creates the builder with the appropiate parameters
     *
     * @return Renderer instance
     */
    public View create() {
        final Renderer renderer;
        if (!isRecyclable(id, view)) {
            renderer = factory.getRenderer(id, postable);
            view = inflater.inflate(id, parent, false);
            renderer.mapViews(view);
            view.setTag(renderer);
        }
        return view;
    }

    /**
     * Checks if a Renderer if compatible with the existing one.
     * <p/>
     * A Renderer has to be recycled (inflated) if
     * - The View is not initialized
     * - The Renderer is null
     * - The Renderable and the cached Renderer have different Renderer IDs (incompatible Layout IDs)
     *
     * @param id   ID of the new Renderer
     * @param view View which Tag contains a Rendered ID
     * @return TRUE if the renderer can be reused, FALSE otherwise
     */
    private boolean isRecyclable(int id, View view) {
        return view != null && view.getTag() != null && id == ((Renderer) view.getTag()).getId();
    }
}