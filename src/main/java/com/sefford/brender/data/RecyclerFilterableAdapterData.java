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
import com.sefford.brender.interfaces.RecyclerAdapterData;
import com.sefford.brender.interfaces.Renderable;

import java.util.ArrayList;
import java.util.List;

/**
 * Base data as the common base of the filterable lineage of AdapterData
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public abstract class RecyclerFilterableAdapterData extends Filter implements RecyclerAdapterData {



    /**
     * Master data of the adapter
     */
    protected final List<Renderable> master;

    /**
     * List containing headers. Headers will be always on top of the list
     */
    protected List<Renderable> headers = new ArrayList<>();

    /**
     * List containing footers. Footers will be always at the end of the list.
     */
    protected List<Renderable> footers = new ArrayList<>();

    /**
     * Filtered data of the adapter
     */
    protected List<Renderable> filtered;

    /**
     * Creates a new instance of FilterableAdapterData
     *
     * @param master
     */
    public RecyclerFilterableAdapterData(List<Renderable> master) {
        this.master = master;
        this.filtered = master;
    }

    @Override
    public int size() {
        return filtered.size();
    }

    @Override
    public long getItemId(int pos) {
        if (pos < headers.size()){
            return headers.get(pos).getRenderableId();
        }else if (pos >= headers.size() && pos < master.size()){
            pos -= headers.size();
            return master.get(pos).getRenderableId();
        }else {
            pos -= (master.size() + headers.size());
            return footers.get(pos).getRenderableId();
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filtered = (List<Renderable>) results.values;
    }

    @Override
    public Filter getFilter() {
        return this;
    }

    @Override
    public Renderable getItem(int pos) {
        if (pos < headers.size()){
            return headers.get(pos);
        }else if (pos >= headers.size() && pos < filtered.size()){
            pos -= headers.size();
            return filtered.get(pos);
        }else {
            pos -= (filtered.size() + headers.size());
            return footers.get(pos);
        }
    }

    @Override
    public void addFooter(Renderable data) {
        footers.add(data);
    }

    @Override
    public void addHeader(Renderable data) {
        headers.add(data);
    }

    @Override
    public void removeFooter(Renderable footer) {
        footers.remove(footer);
    }

    @Override
    public void removeHeader(Renderable header) {
        headers.remove(header);
    }

    @Override
    public int getHeaderViewCount() {
        return headers.size();
    }

    @Override
    public int getFooterViewCount() {
        return footers.size();
    }

}
