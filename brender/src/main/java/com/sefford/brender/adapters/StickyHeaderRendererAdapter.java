/*
 * Copyright (C) 2015 Saúl Díaz, Jorge Rodriguez
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

import android.view.View;
import android.view.ViewGroup;
import com.sefford.brender.interfaces.*;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Renderable adapter that derives from {@link  RendererAdapter RendererAdapter} adding
 * support for sections and headers through StickyListHeaders library. It implements {@link se.emilsjolander.stickylistheaders.StickyListHeadersAdapter}
 * interface to provide {@link se.emilsjolander.stickylistheaders.StickyListHeadersListView} the
 * information it needs to create and obtain the headers.
 *
 * @author Jorge Rodríguez <tylosan@gmail.com>
 * @author Saul Diaz <sefford@gmail.com>
 */

public class StickyHeaderRendererAdapter extends RendererAdapter implements StickyListHeadersAdapter {

    final HeaderIdentifier headerIdentifier;

    /**
     * Creates a new instance of the RendererAdapter
     *
     * @param data             Adapter data
     * @param factory          Factory to instantiate the renderers
     * @param bus              Bus to notify the UI of events on the renderers
     * @param headerIdentifier Header identifier facilities
     */
    public StickyHeaderRendererAdapter(AdapterData data, RendererFactory factory, Postable bus, HeaderIdentifier headerIdentifier) {
        super(data, factory, bus);
        this.headerIdentifier = headerIdentifier;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        convertView = configureRenderer(convertView, parent, headerIdentifier.getRenderableId(getItem(position), position));
        final Renderer renderer = (Renderer) convertView.getTag();
        renderer.render(getItem(position), position, position == 0, position == getCount() - 1);
        renderer.hookUpListeners(getItem(position));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return headerIdentifier.getHeaderId(getItem(position), position);
    }
}