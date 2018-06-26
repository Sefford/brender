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
package com.sefford.brender.data

import android.widget.Filter

import com.sefford.brender.filters.NullFilter
import com.sefford.brender.components.AdapterData
import com.sefford.brender.components.Renderable

/**
 * Adapter for [RecyclerRendererAdapter][com.sefford.brender.adapters.RecyclerRendererAdapter]. Does not filter
 * and does not provide any extras.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
class RecyclerAdapterData
/**
 * Creates a new instance of UnfilterableData
 *
 * @param master External data
 */
(
        /**
         * Master data of the adapter
         */
        protected val master: List<Renderable>) : AdapterData {

    override// AFAIK this is not necessary on RecyclerViews
    val viewTypeCount: Int
        get() = 0

    override fun size(): Int {
        return master.size
    }

    override fun getItemId(pos: Int): Long {
        return master[pos].renderableId.toLong()
    }

    override fun getItem(pos: Int): Renderable {
        return master[pos]
    }

    override fun notifyDataSetChanged() {
        // Empty
    }

    override fun getFilter(): Filter {
        return NullFilter()
    }
}
