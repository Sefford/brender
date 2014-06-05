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
 * Renderable interface will allow objects to provide Renderers IDs.
 *
 * While other implementations are more loose, Brender enforces that this ID must be an unique
 * layout identifier. By doing so we gain some advantages like a straightforward comparison of compatibles
 *
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public interface Renderable {

    /**
     * Gets the Renderer ID of the Renderable.
     *
     * @return ID of the Renderable
     */
    int getRenderableId();
}