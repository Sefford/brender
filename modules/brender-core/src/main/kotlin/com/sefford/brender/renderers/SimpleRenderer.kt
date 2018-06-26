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
package com.sefford.brender.renderers

import android.view.View
import com.sefford.brender.components.Renderer
import android.support.v7.widget.RecyclerView


/**
 * A Renderer is an abstraction of the ViewHolder pattern.
 *
 *
 * It allows to decouple the adapter view logic from the object in rendering, allowing more streamlined
 * getView() logic by delegating it to the object itself.
 *
 *
 * This comes in handy for adapters which have to allow different views to be rendered in the same adapter.
 *
 *
 * This class is a template to start their new Renderer hierarchy
 *
 * @author Saul Diaz<sefford></sefford>@gmail.com>
 */
class SimpleRenderer<T : Any>
/**
 * Creates a new instance of a Renderer
 *
 * @param view View of the Renderer
 */
(view: View) : RecyclerView.ViewHolder(view), Renderer<T> {
    override val id: Int
        get() = 0

    override fun hookUpListeners(renderable: T) {
    }

    override fun render(renderable: T, position: Int, first: Boolean, last: Boolean) {
    }

    override fun refresh(renderable: T, payloads: List<*>) {
    }

    override fun clean() {
    }
}
