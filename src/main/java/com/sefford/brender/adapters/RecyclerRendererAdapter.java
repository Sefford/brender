/*
 * Copyright (C) 2015 Saúl Díaz
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.*;

/**
 * Created by sefford on 27/03/15.
 */
public class RecyclerRendererAdapter extends RecyclerView.Adapter implements Filterable {
    /**
     * Adapter data
     */
    protected final RecyclerAdapterData data;
    protected final RendererFactory factory;
    private RendererListener listener;
    /**
     * Bus to notify the UI of events on the renderers
     */
    protected final Postable postable;

    public RecyclerRendererAdapter(RecyclerAdapterData data, RendererFactory factory, Postable postable) {
        super();
        this.data = data;
        this.postable = postable;
        this.factory = factory;
    }

    public void setRendererListener(RendererListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (RecyclerView.ViewHolder) factory.getRenderer(viewType, postable, getInflater(parent.getContext()).inflate(viewType, parent, false));
    }

    protected LayoutInflater getInflater(Context context) {
        return LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Renderer renderer = (Renderer) holder;
        renderer.setRendererListener(listener);
        renderer.render(data.getItem(position), position, position == 0, position == getItemCount() - 1);
        renderer.hookUpListeners(data.getItem(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (int) data.getItemId(position);
    }


    @Override
    public Filter getFilter() {
        return data.getFilter() == null ? new NullFilter() : data.getFilter();
    }

    public void addHeader(Renderable header) {
        data.addHeader(header);
        notifyDataSetChanged();
    }

    public void removeHeader(Renderable header){
        data.removeHeader(header);
        notifyDataSetChanged();
    }

    public void addFooter(Renderable footer) {
        data.addFooter(footer);
        notifyDataSetChanged();
    }

    public void removeFooter(Renderable footer) {
        data.removeFooter(footer);
        notifyDataSetChanged();
    }

    public int getHeaderViewCount() {
        return data.getHeaderViewCount();
    }

    public int getFooterViewCount() {
        return data.getFooterViewCount();
    }

}
