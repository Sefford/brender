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

package com.sefford.brender.data;

import android.widget.Filter;

import com.sefford.brender.interfaces.AdapterData;
import com.sefford.brender.interfaces.Renderable;

import java.util.List;

/**
 * Base data as the common base of the filterable lineage of AdapterData
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public abstract class FilterableAdapterData extends Filter implements AdapterData {

    /**
     * Master data of the adapter
     */
    protected final List<Renderable> master;
    /**
     * Filtered data of the adapter
     */
    protected List<Renderable> filtered;

    /**
     * Creates a new instance of FilterableAdapterData
     *
     * @param master Master list
     */
    public FilterableAdapterData(List<Renderable> master) {
        this.master = master;
        this.filtered = master;
    }

    @Override
    public int size() {
        return filtered.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public Renderable getItem(int pos) {
        return filtered.get(pos);
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filtered = (List<Renderable>) results.values;
    }

    @Override
    public Filter getFilter() {
        return this;
    }
}
