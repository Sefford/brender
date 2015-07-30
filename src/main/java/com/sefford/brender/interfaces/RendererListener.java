package com.sefford.brender.interfaces;

import android.view.View;

/**
 * Created by alejandro on 30/07/15.
 */
public interface RendererListener {

    void onRenderEvent(int actionId, Object renderable, int position, View view);

}
