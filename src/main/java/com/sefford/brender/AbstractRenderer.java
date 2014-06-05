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
package com.sefford.brender;

import com.sefford.brender.interfaces.Postable;
import com.sefford.brender.interfaces.Renderer;

/**
 * A Renderer is an abstraction of the ViewHolder pattern.
 * <p/>
 * It allows to decouple the adapter view logic from the object in rendering, allowing more streamlined
 * getView() logic by delegating it to the object itself.
 * <p/>
 * This comes in handy for adapters which have to allow different views to be rendered in the same adapter.
 *
 * @author Saul Diaz<sefford@gmail.com>
 */
public abstract class AbstractRenderer<T> implements Renderer<T> {


    /**
     * ID of the Renderer.
     * <p/>
     * In order for it to be used seamlessly, it is good
     * that this ID is actually the ID of the Layout; in this way, we can compare
     * if two renderers are compatible if they have the same Layout ID, and directly
     * inflate the view from the Renderer ID.
     */
    protected final int id;
    /**
     * Bus manager to notify events to the UI
     */
    protected final Postable postable;

    /**
     * Creates a new instance of a Renderer
     *
     * @param id       Building ID of the renderer
     * @param postable Bus Manager to notify the event to the UI
     */
    public AbstractRenderer(int id, Postable postable) {
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
