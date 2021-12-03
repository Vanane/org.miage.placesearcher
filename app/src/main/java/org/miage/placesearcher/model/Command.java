package org.miage.placesearcher.model;

import android.graphics.Bitmap;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;
import com.google.gson.annotations.Expose;

@Table(name = "Command")
public class Command extends Model {
    @Expose
    public PlaceAddress address;

    @Expose
    @Column(name = "signature", unique = false)
    public byte[] signature;


}
