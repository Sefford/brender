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
import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.AdapterData;
import com.sefford.brender.interfaces.Renderable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter Data which does not filter data and does not provide a Extra.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public final class DefaultAdapterData implements AdapterData {

    /**
     * Master data of the adapter
     */
    protected final List<Renderable> master;
    /**
     * Number of types of views inside the Data
     */
    protected final Set<Integer> viewTypes;

    /**
     * Creates a new instance of UnfilterableData
     *
     * @param master External data
     */
    public DefaultAdapterData(List<Renderable> master) {
        this.master = master;
        this.viewTypes = new HashSet<>();
        computeViewTypes(master);
    }

    /**
     * Computes the number of different View Types inside the AdapterData
     *
     * @param master List of the data
     */
    protected void computeViewTypes(List<Renderable> master) {
        for (Renderable renderable : master) {
            viewTypes.add(renderable.getRenderableId());
        }
    }

    @Override
    public int size() {
        return master.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public Renderable getItem(int pos) {
        return master.get(pos);
    }

    @Override
    public int getViewTypeCount() {
        return viewTypes.size();
    }

    @Override
    public void notifyDataSetChanged() {
        viewTypes.clear();
        computeViewTypes(master);
    }

    @Override
    public Filter getFilter() {
        return new NullFilter();
    }


}
