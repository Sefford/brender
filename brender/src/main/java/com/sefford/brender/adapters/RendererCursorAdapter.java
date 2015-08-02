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
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefford.brender.data.CursorAdapterData;
import com.sefford.brender.interfaces.AdapterData;
import com.sefford.brender.interfaces.Renderer;
import com.sefford.brender.interfaces.RendererFactory;
import com.sefford.common.interfaces.Postable;

/**
 * Renderer Cursor Adapter applies the same ideas seen on {@link RendererAdapter RendererAdapter} to
 * a CursorAdapter. This is useful to apply Renderers into {@link android.widget.SearchView SearchView} suggestions
 * or autocomplete features.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class RendererCursorAdapter extends CursorAdapter {

    /**
     * Factory to instantiate the renderers
     */
    protected final RendererFactory factory;
    /**
     * Notification system
     */
    protected final Postable bus;

    /**
     * Default Constructor of RendererCursorAdapter as recommended by Android CursorAdapter's
     * guidelines.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     * @param factory Factory to instantiate the renderer
     * @param bus     Notification system
     */
    public RendererCursorAdapter(Context context, CursorAdapterData c, int flags, RendererFactory factory, Postable bus) {
        super(context, c, flags);
        this.factory = factory;
        this.bus = bus;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = getInflater(context).inflate(((AdapterData) cursor).getItem(cursor.getPosition()).getRenderableId(), parent, false);
        view.setTag(factory.getRenderer(((AdapterData) cursor).getItem(cursor.getPosition()).getRenderableId(), bus, view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Renderer renderer = (Renderer) view.getTag();
        final int position = cursor.getPosition();
        renderer.render(((AdapterData) cursor).getItem(position), position, position == 0, position == getCount() - 1);
        renderer.hookUpListeners(((AdapterData) cursor).getItem(position));
    }

    @NonNull
    protected LayoutInflater getInflater(Context context) {
        return LayoutInflater.from(context);
    }
}
