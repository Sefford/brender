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
package com.sefford.brender.interfaces;

/**
 * Abstracts the behavior of a Renderer.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public interface Renderer<T> {

    /**
     * Hooks up the listeners of the Renderer
     */
    void hookUpListeners(T renderable);

    /**
     * Renders the view
     *
     * @param renderable Renderable item to set the info from
     * @param first      Is first of the list
     * @param last       is last of the list
     */
    void render(T renderable, int position, boolean first, boolean last);

    /**
     * Releases ViewHolder resources
     */
    void clean();

    /**
     * Unique ID of the Renderer
     *
     * @return Renderer ID
     */
    int getId();
}