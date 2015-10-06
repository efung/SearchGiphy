package com.github.efung.searchgiphy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.efung.searchgiphy.R;

/**
 * @author efung on 2015 Sep 16
 */
public class ImagesMetadataViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title;
    private OnItemClickListener<ImagesMetadataViewHolder> listener;

    public ImagesMetadataViewHolder(View itemView, final OnItemClickListener<ImagesMetadataViewHolder> listener) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, ImagesMetadataViewHolder.this, getAdapterPosition());
                }
            }
        });
        this.listener = listener;
    }
}
