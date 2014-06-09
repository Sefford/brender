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
import com.sefford.brender.interfaces.RendererBuilder;
import com.sefford.brender.interfaces.RendererFactory;

/**
 * Builder for Renderable classes.
 * <p/>
 * While the Builder will take most of outside requirements to build the Renderer, including a Factory to instantiate
 * the Renderers.
 * <p/>
 * While the Builder is considered to have a correct way to initialize the Renderers, there is still a necessity for
 * the developer to build their own. Insted of inheriting from the Builder, the developers can compose it through
 * a {@link RendererFactory Factory}.
 * As the Builder comes pre-tested, the developers can deploy their own customized factories, ensuring the developer
 * will only need to test their own.
 * The Renderer Builder is still extensible, so the developer can take their own decisions on extending the adapter.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class Builder<T extends Object> implements RendererBuilder<T> {

    /**
     * Renderer Factory to help with the instantiation.
     * <p/>
     */
    protected RendererFactory<T> factory;
    /**
     * ID of the Renderer to instantiate
     *
     * @see com.sefford.brender.interfaces.Renderable#getRenderableId()
     */
    protected int id;
    /**
     * Layout inflater to use to build the layout of the view
     */
    protected LayoutInflater inflater;
    /**
     * Parent where to attach the view.
     */
    protected ViewGroup parent;
    /**
     * View to instantiate and tag the Render
     */
    protected View view;
    /**
     * Postable Interface to notify changes to the UI
     */
    protected Postable postable;
    /**
     * Extras configuration for the Renderers
     */
    protected T extras;

    /**
     * Instantiates a new Renderer Builder instance.
     *
     * @param factory Renderer Factory to build the Renderers.
     */
    public Builder(RendererFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public RendererBuilder instantiate(int id) {
        this.id = id;
        return this;
    }

    @Override
    public RendererBuilder into(View view) {
        this.view = view;
        return this;
    }

    @Override
    public RendererBuilder using(LayoutInflater inflater) {
        this.inflater = inflater;
        return this;
    }

    @Override
    public RendererBuilder inside(ViewGroup parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public RendererBuilder addingConfiguratior(T extras) {
        this.extras = extras;
        return this;
    }

    @Override
    public Builder interactingWith(Postable postable) {
        this.postable = postable;
        return this;
    }

    /**
     * Creates the builder with the appropiate parameters.
     * <p/>
     * The method will first try to assess if the Builder is in the correct state by performing a sanity check.
     * <p/>
     * Then there are two steps that can happen. If the View's renderer is found not to be able to be recycled (because
     * of nullity or incompatible Renderers), the factory will obtain the correct Renderer and the view will be
     * re-instantiated.
     * <p/>
     * Otherwise, the view and its Renderer will be returned directly.
     *
     * @return Renderer instance
     * @throws IllegalStateException if the Builder is not correctly initialized
     */
    @Override
    public View create() {
        if (!isBuilderInitialized()) {
            throw new IllegalStateException(id == 0 ? "Builder requires a valid ID" :
                    factory == null ? "Factory is null" : "Builder requires a non-null inflater");
        }
        final Renderer renderer;
        if (!isRecyclable(id, view)) {
            renderer = factory.getRenderer(id, postable, extras);
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
     * @see com.sefford.brender.interfaces.Renderable
     */
    protected boolean isRecyclable(int id, View view) {
        return view != null && view.getTag() != null && id == ((Renderer) view.getTag()).getId();
    }

    /**
     * Returns if the Builder is correctly initialized.
     * <p/>
     * Only the ID of the renderer, the inflater and the factory are necessary for the Builder to have a consistent state.
     * <p/>
     * Many views do not require a ViewGroup straightforward and eventually is added to it during the flow of the view. Similarly,
     * extras might not be required by your Renderers to work. In the case of Adapters, the initial view is null, therefore
     * the Builder will consider the Renderer {@link #isRecyclable(int, android.view.View) not suitable to be recycled}.
     *
     * @return TRUE if renderable ID is not 0 and Layout Inflater not null and the Factory is not null, FALSE otherwise.
     */
    public boolean isBuilderInitialized() {
        return id != 0 && inflater != null && factory != null;
    }
}