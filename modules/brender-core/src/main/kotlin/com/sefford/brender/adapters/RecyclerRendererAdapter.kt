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
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sefford.brender.components.Renderable
import com.sefford.brender.components.Renderer
import com.sefford.brender.components.RendererFactory
import com.sefford.brender.data.RecyclerAdapterData
import com.sefford.common.interfaces.Postable

/**
 * Renderable adapter to mix several Renderable types for RecyclerView.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
class RecyclerRendererAdapter(protected val data: RecyclerAdapterData,
                              /**
                               * Factory to instantiate the renderers
                               */
                              protected val factory: RendererFactory,
                              /**
                               * Bus to notify the UI of events on the renderers
                               */
                              protected val postable: Postable)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val isEmpty: Boolean
        get() = data.size() == 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return factory.getRenderer(viewType, postable, getInflater(parent.context)
                .inflate(viewType, parent, false)) as RecyclerView.ViewHolder
    }

    fun getInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int,
                                  payloads: List<Any>) {
        if (!payloads.isEmpty()) {
            (holder as Renderer<Any>).refresh(data.getItem(position), payloads)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Do nothing, we do all the stuff of onViewAttachedToWindow
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.adapterPosition
        (holder as Renderer<Any>).render(data.getItem(position),
                position,
                position == 0,
                position == itemCount - 1)
        (holder as Renderer<Any>).hookUpListeners(data.getItem(position))
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as Renderer<*>).clean()
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as Renderer<*>).clean()
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun getItemViewType(position: Int): Int {
        return data.getItemId(position).toInt()
    }

    fun getItemAt(position: Int): Renderable {
        return data.getItem(position)
    }
}
