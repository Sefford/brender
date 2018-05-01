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
import com.sefford.brender.components.AdapterData
import com.sefford.brender.components.Renderable
import com.sefford.brender.filters.NullFilter
import java.util.*

/**
 * Adapter Data which does not filter data and does not provide a Extra.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
class DefaultAdapterData
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
    /**
     * Number of types of views inside the Data
     */
    val viewTypes: MutableSet<Int>

    override val viewTypeCount: Int
        get() = viewTypes.size

    init {
        this.viewTypes = HashSet()
        computeViewTypes(master)
    }

    /**
     * Computes the number of different View Types inside the AdapterData
     *
     * @param master List of the data
     */
    fun computeViewTypes(master: List<Renderable>) {
        for (renderable in master) {
            viewTypes.add(renderable.renderableId)
        }
    }

    override fun size(): Int {
        return master.size
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getItem(pos: Int): Renderable {
        return master[pos]
    }

    override fun notifyDataSetChanged() {
        viewTypes.clear()
        computeViewTypes(master)
    }

    override fun getFilter(): Filter {
        return NullFilter()
    }


}
