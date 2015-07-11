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

import android.view.View;

import com.sefford.common.interfaces.Postable;

/**
 * Abstraction for the Renderer Factory Interface
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public interface RendererFactory {
    /**
     * Creates a new Renderer Instance.
     *
     * @param id       ID of the Renderer to instantiate
     * @param postable Postable Interface to send events to the UI
     * @param view     View to initialize the Renderer
     * @return An initialized instance of a Renderer
     */
    Renderer getRenderer(int id, Postable postable, View view);
}
