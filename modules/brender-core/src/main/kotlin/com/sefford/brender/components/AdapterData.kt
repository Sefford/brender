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
package com.sefford.brender.components

import android.widget.Filterable
import com.sefford.brender.adapters.RendererAdapter

/**
 * Adapter element created for adapting to RendererAdapter interface
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
interface AdapterData : Filterable {

    /**
     * Returns the number different of classes of views contained inside the AdapterData.
     * Due to Brenders architecture this is usually composed of the
     * different [Renderable IDs][Renderable.getRenderableId] of the Renderables.
     *
     * @return Number of different classes of views
     */
    val viewTypeCount: Int

    /**
     * Returns the size of the held data
     *
     * @return Size of the held data
     */
    fun size(): Int

    /**
     * Retrieves the item ID
     *
     * @param pos Position of the element
     * @return ID of the element
     */
    fun getItemId(pos: Int): Long

    /**
     * Retrieves the element on position pos
     *
     * @param pos Position of the element
     * @return Renderable at position pos
     */
    fun getItem(pos: Int): Renderable

    /**
     * Convenience method to update the [View Type Count][.getViewTypeCount] of the AdapterData.
     *
     *
     * This is called together with the [RendererAdapter.notifyDataSetChanged].
     */
    fun notifyDataSetChanged()

}
