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
package com.sefford.brender.interfaces;

import android.widget.Filterable;
import com.sefford.brender.adapters.RendererAdapter;

/**
 * Adapter element created for adapting to RendererAdapter interface
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public interface AdapterData extends Filterable {

    /**
     * Returns the size of the held data
     *
     * @return Size of the held data
     */
    int size();

    /**
     * Retrieves the item ID
     *
     * @param pos Position of the element
     * @return ID of the element
     */
    long getItemId(int pos);

    /**
     * Retrieves the element on position pos
     *
     * @param pos Position of the element
     * @return Renderable at position pos
     */
    Renderable getItem(int pos);

    /**
     * Returns the number different of classes of views contained inside the AdapterData.
     * Due to Brenders architecture this is usually composed of the different {@link Renderable#getRenderableId() Renderable IDs}
     * of the Renderables.
     *
     * @return Number of different classes of views
     */
    int getViewTypeCount();

    /**
     * Convenience method to update the {@link #getViewTypeCount() View Type Count} of the AdapterData.
     * <p/>
     * This is called together with the {@link RendererAdapter#notifyDataSetChanged()}.
     */
    void notifyDataSetChanged();

}
