package com.github.efung.searchgiphy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author efung on 2015 Sep 23
 */
public interface OnItemClickListener<T extends RecyclerView.ViewHolder> {
    void onItemClick(View itemView, T viewHolder, int adapterPosition);
}
