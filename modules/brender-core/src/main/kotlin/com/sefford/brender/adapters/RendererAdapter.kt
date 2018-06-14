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
package com.sefford.brender.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.sefford.brender.components.AdapterData
import com.sefford.brender.components.Renderable
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.RendererFactory
import com.sefford.brender.filters.NullFilter
import com.sefford.common.interfaces.Postable

/**
 * Renderable adapter to mix several Renderable types.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
@Deprecated("Use RecyclerView and RecyclerRenderer adapter instead")
open class RendererAdapter
/**
 * Creates a new instance of the RendererAdapter
 *
 * @param data     Adapter data
 * @param factory  Builder to instantiate the renderers
 * @param postable Bus to notify the UI of events on the renderers
 */
@JvmOverloads constructor(
        /**
         * Adapter data
         */
        protected val data: AdapterData,
        /**
         * Builder to instantiate the Renderers
         */
        protected val factory: RendererFactory,
        /**
         * Bus to notify the UI of events on the renderers
         */
        protected val postable: Postable,
        /**
         * Inflater creator to allow testing
         */
        protected val inflaterCreator: InflaterCreator = DefaultInflaterCreator) : BaseAdapter(), Filterable {

    override fun getCount(): Int {
        return data.size()
    }

    override fun getItem(position: Int): Renderable {
        return data.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return data.getItemId(position)
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertedView = convertView
        val renderable: Renderable = getItem(position)

        convertedView = configureRenderer(convertedView, parent, renderable.renderableId)
        val rendererInterface = convertedView.tag as Renderer<Any>
        rendererInterface.hookUpListeners(renderable)
        rendererInterface.render(renderable, position, position == 0, position == count - 1)
        return convertedView
    }

    fun configureRenderer(convertView: View, parent: ViewGroup, renderableId: Int): View {
        if (!isAdapterInitialized(renderableId)) {
            throw IllegalStateException(if (renderableId == 0)
                "Builder requires a valid ID"
            else
                "Factory is null")
        }
        return if (!isRecyclable(renderableId, convertView)) {
            val convertedView = inflaterCreator.createInflater(parent.context).inflate(renderableId, parent, false)
            val renderer = factory.getRenderer(renderableId, postable, convertedView)
            convertedView.tag = renderer
            convertedView
        } else {
            convertView
        }
    }

    /**
     * Checks if a Renderer if compatible with the existing one.
     *
     *
     * A Renderer has to be recycled (inflated) if
     * - The View is not initialized
     * - The Renderer is null
     * - The Renderable and the cached Renderer have different Renderer IDs (incompatible Layout IDs)
     *
     * @param id   ID of the new Renderer
     * @param view View which Tag contains a Rendered ID
     * @return TRUE if the renderer can be reused, FALSE otherwise
     * @see com.sefford.brender.components.Renderable
     */
    fun isRecyclable(id: Int, view: View?): Boolean {
        return (view?.tag as? Renderer<*>)?.id == id
    }

    /**
     * Returns if the Adapter is correctly initialized.
     *
     *
     * Only the ID of the renderer, the inflater and the factory are necessary for the Adapter to have a consistent state.
     *
     *
     * Many views do not require a ViewGroup straightforward and eventually is added to it during the flow of the view. Similarly,
     * extras might not be required by your Renderers to work. In the case of Adapters, the initial view is null, therefore
     * the Factory will consider the Renderer [not suitable to be recycled][.isRecyclable].
     *
     * @param renderableId ID of the [Renderable]
     * @return TRUE if renderable ID is not 0 and Layout Inflater not null and the Factory is not null, FALSE otherwise.
     */
    fun isAdapterInitialized(renderableId: Int): Boolean {
        return renderableId > 0
    }

    override fun getFilter(): Filter {
        return if (data.filter == null) NullFilter() else data.filter
    }

    override fun getViewTypeCount(): Int {
        // Because there can't be an adapter with 0 type of views
        return Math.max(data.viewTypeCount, 1)
    }

    override fun notifyDataSetChanged() {
        data.notifyDataSetChanged()
        super.notifyDataSetChanged()
    }

    override fun notifyDataSetInvalidated() {
        data.notifyDataSetChanged()
        super.notifyDataSetInvalidated()
    }

    open class InflaterCreator {

        fun createInflater(context: Context): LayoutInflater {
            return LayoutInflater.from(context)
        }
    }

    object DefaultInflaterCreator : InflaterCreator()
}