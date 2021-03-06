package com.github.efung.searchgiphy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.efung.searchgiphy.R;
import com.github.efung.searchgiphy.model.GiphyImage;
import com.github.efung.searchgiphy.model.ImagesMetadata;
import com.github.efung.searchgiphy.util.DrawableHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author efung on 2015 Sep 16
 */
public class GiphyResultAdapter extends RecyclerView.Adapter<ImagesMetadataViewHolder> {
    private Context context;
    private List<ImagesMetadata> items;
    private OnItemClickListener<ImagesMetadataViewHolder> itemClickListener;


    public GiphyResultAdapter(Context context, List<ImagesMetadata> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(List<ImagesMetadata> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<ImagesMetadataViewHolder> listener) {
        itemClickListener = listener;
    }

    public ImagesMetadata getItem(int position) {
        return items.get(position);
    }

    @Override
    public ImagesMetadataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_cell, null);
        ImagesMetadataViewHolder holder = new ImagesMetadataViewHolder(layoutView, itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImagesMetadataViewHolder holder, int position) {
        ImagesMetadata data = items.get(position);
        GiphyImage image = null;
        if (data.images != null) {
            if (data.images.original_still != null) {
                image = data.images.original_still;
            }
        }
        if (image != null) {
            Picasso.with(context).load(image.url).placeholder(DrawableHelper.getRandomColorDrawable(context)).into(holder.image);
            if (!TextUtils.isEmpty(data.caption)) {
                holder.title.setText(data.caption);
            }
        }
    }

    @Override
    public int getItemCount () {
        return items == null ? 0 : items.size();
    }
}
