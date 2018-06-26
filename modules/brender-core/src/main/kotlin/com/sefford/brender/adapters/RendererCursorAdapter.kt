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
package com.sefford.brender.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.sefford.brender.components.AdapterData
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.RendererFactory
import com.sefford.brender.data.CursorAdapterData
import com.sefford.common.interfaces.Postable

/**
 * Renderer Cursor Adapter applies the same ideas seen on [RendererAdapter] to
 * a CursorAdapter. This is useful to apply Renderers into [SearchView][android.widget.SearchView] suggestions
 * or autocomplete features.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
class RendererCursorAdapter
/**
 * Default Constructor of RendererCursorAdapter as recommended by Android CursorAdapter's
 * guidelines.
 *
 * @param context The context
 * @param c       The cursor from which to get the data.
 * @param flags   Flags used to determine the behavior of the adapter; may
 * be any combination of [.FLAG_AUTO_REQUERY] and
 * [.FLAG_REGISTER_CONTENT_OBSERVER].
 * @param factory Factory to instantiate the renderer
 * @param bus     Notification system
 */
(context: Context, c: CursorAdapterData, flags: Int,
 /**
  * Factory to instantiate the renderers
  */
 protected val factory: RendererFactory,
 /**
  * Notification system
  */
 protected val bus: Postable) : CursorAdapter(context, c, flags) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val view = getInflater(context).inflate(
                (cursor as AdapterData).getItem(cursor.position).renderableId,
                parent,
                false)
        view.tag = factory.getRenderer((cursor as AdapterData)
                .getItem(cursor.position).renderableId, bus, view)
        return view
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val renderer = view.tag as Renderer<Any>
        val position = cursor.position
        renderer.render((cursor as AdapterData).getItem(position),
                position,
                position == 0,
                position == count - 1)
        renderer.hookUpListeners((cursor as AdapterData).getItem(position))
    }

    fun getInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }
}
