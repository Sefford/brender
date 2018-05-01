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

package com.sefford.brender.data

import android.widget.Filter

import com.sefford.brender.components.Renderable
import com.sefford.brender.components.AdapterData

/**
 * Base data as the common base of the filterable lineage of AdapterData
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
abstract class FilterableAdapterData
/**
 * Creates a new instance of FilterableAdapterData
 *
 * @param master Master list
 */
(
        /**
         * Master data of the adapter
         */
        protected val master: List<Renderable>) : Filter(), AdapterData {
    /**
     * Filtered data of the adapter
     */
    protected var filtered: List<Renderable>

    init {
        this.filtered = master
    }

    override fun size(): Int {
        return filtered.size
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getItem(pos: Int): Renderable {
        return filtered[pos]
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
        filtered = results.values as List<Renderable>
    }

    override fun getFilter(): Filter {
        return this
    }
}
