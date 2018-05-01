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
package com.sefford.brender.components

/**
 * Abstracts the behavior of a Renderer.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
interface Renderer<T> {

    /**
     * Unique ID of the Renderer
     *
     * @return Renderer ID
     */
    val id: Int

    /**
     * Hooks up the listeners of the Renderer
     */
    fun hookUpListeners(renderable: T)

    /**
     * Renders the view
     *
     * @param renderable Renderable item to set the info from
     * @param position current position of the adapter
     * @param first      Is first of the list
     * @param last       is last of the list
     */
    fun render(renderable: T, position: Int, first: Boolean, last: Boolean)

    /**
     * Refreshes the renderer with the payloads it received
     *
     * @param renderable Renderable item to set the info from
     * @param payloads
     */
    fun refresh(renderable: T, payloads: List<*>)

    /**
     * Releases ViewHolder resources
     */
    fun clean()
}