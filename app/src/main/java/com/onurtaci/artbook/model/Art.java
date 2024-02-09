package com.onurtaci.artbook.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Entity
public class Art implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "art_name")
    public String artName;
    @ColumnInfo(name = "artist_name")
    public String artistName;
    @ColumnInfo(name = "art_year")
    public String artYear;

    @ColumnInfo(name = "art_image")
    public byte[] image;

    public Art(String artName, String artistName, String artYear, byte[] image) {
        this.artName = artName;
        this.artistName = artistName;
        this.artYear = artYear;
        this.image = image;
    }

    public Art(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
}
