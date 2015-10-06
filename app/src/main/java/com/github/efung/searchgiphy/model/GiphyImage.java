package com.github.efung.searchgiphy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author efung on 2015 Sep 15
 */
public class GiphyImage implements Parcelable {
    public String url;
    public int width;
    public int height;
    public long size;
    public int frames;
    public String mp4;
    public long mp4_size;
    public String webp;
    public long webp_size;


    public static final Parcelable.Creator<GiphyImage> CREATOR =
            new Parcelable.Creator<GiphyImage>() {
                @Override
                public GiphyImage createFromParcel(Parcel in) {
                    return new GiphyImage(in);
                }

                @Override
                public GiphyImage[] newArray(int i) {
                    return new GiphyImage[i];
                }
            };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(url);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeLong(size);
        parcel.writeInt(frames);
        parcel.writeString(mp4);
        parcel.writeLong(mp4_size);
        parcel.writeString(webp);
        parcel.writeLong(webp_size);
    }

    private GiphyImage(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        size = in.readLong();
        frames = in.readInt();
        mp4 = in.readString();
        mp4_size = in.readLong();
        webp = in.readString();
        webp_size = in.readLong();
    }
}
