package com.sefford.brender.filters;

import android.widget.Filter;

/**
 * Null filter for RendererAdapter.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class NullFilter extends Filter {

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        // Do nothing
    }
}
