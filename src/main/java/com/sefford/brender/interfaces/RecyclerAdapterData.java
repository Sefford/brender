package com.sefford.brender.interfaces;

import android.widget.Filterable;

/**
 * @author Alejandro Rodriguez <alexrs95@gmail.com>
 */
public interface RecyclerAdapterData extends Filterable {

    /**
     * Returns the size of the held data
     *
     * @return Size of the held data
     */
    int size();

    /**
     * Retrieves the item ID
     *
     * @param pos Position of the element
     * @return ID of the element
     */
    long getItemId(int pos);

    /**
     * Retrieves the element on position pos
     *
     * @param pos Position of the element
     * @return Renderable at position pos
     */
    Renderable getItem(int pos);


    /**
     * Add a renderable as a footer
     * @param data
     */
    void addFooter(Renderable data);


    /**
     * Add a renderable as a header
     * @param data
     */
    void addHeader(Renderable data);

    void removeFooter(Renderable footer);

    void removeHeader(Renderable header);

    int getHeaderViewCount();

    int getFooterViewCount();
}
