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
package com.sefford.brender.data;

import android.widget.Filter;
import com.sefford.brender.filters.NullFilter;
import com.sefford.brender.interfaces.AdapterData;
import com.sefford.brender.interfaces.Renderable;

import java.util.List;

/**
 * Adapter for {@link com.sefford.brender.adapters.RecyclerRendererAdapter RecyclerRendererAdapter}. Does not filter
 * and does not provide any extras.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class RecyclerAdapterData implements AdapterData {

    /**
     * Master data of the adapter
     */
    protected final List<Renderable> master;

    /**
     * Creates a new instance of UnfilterableData
     *
     * @param master External data
     */
    public RecyclerAdapterData(List<Renderable> master) {
        this.master = master;
    }

    @Override
    public int size() {
        return master.size();
    }

    @Override
    public long getItemId(int pos) {
        return master.get(pos).getRenderableId();
    }

    @Override
    public Renderable getItem(int pos) {
        return master.get(pos);
    }

    @Override
    public Filter getFilter() {
        return new NullFilter();
    }
}
