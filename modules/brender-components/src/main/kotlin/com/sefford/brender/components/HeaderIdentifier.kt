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
package com.sefford.brender.components

/**
 * Makes a header identify its ID
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
interface HeaderIdentifier<T : Renderable> {

    /**
     * Gets the Header Renderable ID
     *
     * @param renderable Renderable element
     * @param position   Position of the element
     * @return R.layout-based ID
     * @see {com.sefford.brender.interfaces.Renderable Renderable}
     */
    fun getRenderableId(renderable: T, position: Int): Int

    /**
     * Gets the Header ID
     *
     * @param renderable Renderable element
     * @param position   Position of the element
     * @return Long, abritrary ID
     */
    fun getHeaderId(renderable: T, position: Int): Long
}
