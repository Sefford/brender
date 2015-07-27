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
import com.sefford.brender.interfaces.RecyclerAdapterData;
import com.sefford.brender.interfaces.Renderable;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for {@link com.sefford.brender.adapters.RecyclerRendererAdapter RecyclerRendererAdapter}. Does not filter
 * and does not provide any extras.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class DefaultRecyclerAdapterData implements RecyclerAdapterData {

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
     * Creates a new instance of UnfilterableData
     *
     * @param master External data
     */
    public DefaultRecyclerAdapterData(List<Renderable> master) {
        this.master = master;
    }

    @Override
    public int size() {
        return headers.size() + master.size() + footers.size();
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
    public Renderable getItem(int pos) {
        if (pos < headers.size()){
            return headers.get(pos);
        }else if (pos >= headers.size() && pos < master.size()){
            pos -= headers.size();
            return master.get(pos);
        }else {
            pos -= (master.size() + headers.size());
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


    @Override
    public Filter getFilter() {
        return new NullFilter();
    }


}
