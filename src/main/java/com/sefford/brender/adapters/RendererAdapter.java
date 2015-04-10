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
package com.sefford.brender.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.*;

/**
 * Renderable adapter to mix several Renderable types.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class RendererAdapter extends BaseAdapter implements Filterable {

    /**
     * Adapter data
     */
    protected final AdapterData data;
    /**
     * Builder to instantiate the Renderers
     */
    protected final RendererFactory factory;
    /**
     * Bus to notify the UI of events on the renderers
     */
    protected final Postable postable;

    /**
     * Creates a new instance of the RendererAdapter
     *
     * @param data     Adapter data
     * @param factory  Builder to instantiate the renderers
     * @param postable Bus to notify the UI of events on the renderers
     */
    public RendererAdapter(AdapterData data, RendererFactory factory, Postable postable) {
        super();
        this.data = data;
        this.factory = factory;
        this.postable = postable;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Renderable getItem(int position) {
        return data.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return data.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Renderable renderable = getItem(position);
        convertView = configureRenderer(convertView, parent, renderable.getRenderableId());
        final Renderer rendererInterface = (Renderer) convertView.getTag();
        rendererInterface.hookUpListeners(renderable);
        rendererInterface.render(renderable, position, position == 0, position == getCount() - 1);
        return convertView;
    }

    protected View configureRenderer(View convertView, ViewGroup parent, int renderableId) {
        if (!isAdapterInitialized(renderableId)) {
            throw new IllegalStateException(renderableId == 0 ? "Builder requires a valid ID" :
                    "Factory is null");
        }
        final Renderer renderer;
        if (!isRecyclable(renderableId, convertView)) {
            renderer = factory.getRenderer(renderableId, postable, convertView);
            convertView = createInflater(parent.getContext()).inflate(renderableId, parent, false);
            convertView.setTag(renderer);
        }
        return convertView;
    }

    protected LayoutInflater createInflater(Context context) {
        return LayoutInflater.from(context);
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
     * Returns if the Adapter is correctly initialized.
     * <p/>
     * Only the ID of the renderer, the inflater and the factory are necessary for the Adapter to have a consistent state.
     * <p/>
     * Many views do not require a ViewGroup straightforward and eventually is added to it during the flow of the view. Similarly,
     * extras might not be required by your Renderers to work. In the case of Adapters, the initial view is null, therefore
     * the Factory will consider the Renderer {@link #isRecyclable(int, android.view.View) not suitable to be recycled}.
     *
     * @param renderableId ID of the {@link Renderable Renderable}
     * @return TRUE if renderable ID is not 0 and Layout Inflater not null and the Factory is not null, FALSE otherwise.
     */
    public boolean isAdapterInitialized(int renderableId) {
        return renderableId > 0 && factory != null;
    }

    @Override
    public Filter getFilter() {
        return data.getFilter() == null ? new NullFilter() : data.getFilter();
    }
}