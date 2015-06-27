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
package com.sefford.brender.renderers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderer;

/**
 * A Renderer is an abstraction of the ViewHolder pattern.
 * <p/>
 * It allows to decouple the adapter view logic from the object in rendering, allowing more streamlined
 * getView() logic by delegating it to the object itself.
 * <p/>
 * This comes in handy for adapters which have to allow different views to be rendered in the same adapter.
 * <p/>
 * This class is a template to start their new Renderer hierarchy
 *
 * @author Saul Diaz<sefford@gmail.com>
 */
public abstract class AbstractRenderer<T extends Object> extends RecyclerView.ViewHolder implements Renderer<T> {


    /**
     * ID of the Renderer.
     * <p/>
     * This ID is supposed to be a valid R.layout.id of Android.
     *
     * @see com.sefford.brender.interfaces.Renderable
     */
    protected final int id;
    /**
     * Postable interface to notify events to the UI
     */
    protected final Postable postable;

    /**
     * Creates a new instance of a Renderer
     *
     * @param id       Building ID of the renderer
     * @param postable Postable interface to notify events to the UI
     */
    public AbstractRenderer(int id, Postable postable, View view) {
        super(view);
        this.id = id;
        this.postable = postable;
    }

    /**
     * Unique ID of the Renderer
     *
     * @return Renderer ID
     */
    @Override
    public int getId() {
        return id;
    }
}
