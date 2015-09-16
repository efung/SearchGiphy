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

    public ImagesMetadataViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
    }
}
