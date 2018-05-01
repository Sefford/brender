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
 * Renderable interface will allow objects to provide Renderers IDs.
 *
 *
 * While other implementations are more loose, Brender default behavior enforces that this ID must be an unique
 * layout identifier.
 *
 *
 * By doing so we gain some advantages like a straightforward comparison of compatible Renderers,
 * where several objects can be rendered with the same layout ID, thus reducing the code base for Renderer classes.
 *
 *
 * If you want to Render unrelated elements, a good tip is to target a common Interface for the Renderer.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 */
interface Renderable {

    /**
     * Gets the Renderer ID of the Renderable.
     *
     * @return ID of the Renderable
     */
    val renderableId: Int
}