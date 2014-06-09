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
import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderable;
import com.sefford.brender.interfaces.Renderer;
import com.sefford.brender.interfaces.RendererBuilder;

import java.util.List;

/**
 * Renderable adapter to mix several Renderable types.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class RendererAdapter extends BaseAdapter {

    /**
     * Adapter data
     */
    protected final List<Renderable> data;
    /**
     * Builder to instantiate the Renderers
     */
    protected final RendererBuilder builder;
    /**
     * Bus to notify the UI of events on the renderers
     */
    protected final Postable postable;
    /**
     * Context to provide different Android services
     */
    protected final Context context;
    /**
     * Inflater to build the views
     */
    protected final LayoutInflater inflater;
    /**
     * Extras item
     */
    protected final Object extras;

    /**
     * Creates a new instance of the RendererAdapter
     *
     * @param context  Activity context
     * @param data     Adapter data
     * @param builder  Builder to instantiate the renderers
     * @param postable Bus to notify the UI of events on the renderers
     * @param extras   Extra configuration for the renderer
     */
    public RendererAdapter(Context context, List<Renderable> data, RendererBuilder builder, Postable postable, Object extras) {
        super();
        this.data = data;
        this.builder = builder;
        this.postable = postable;
        this.context = context;
        this.extras = extras;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Renderable getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Renderable renderable = getItem(position);
        convertView = builder.instantiate(renderable.getRenderableId())
                .into(convertView)
                .inside(parent)
                .using(inflater)
                .interactingWith(postable)
                .addingConfiguratior(extras)
                .create();
        final Renderer rendererInterface = (Renderer) convertView.getTag();
        rendererInterface.hookUpListeners(convertView, renderable);
        rendererInterface.render(context, renderable, position, position == 0, position == getCount() - 1);
        return convertView;
    }
}