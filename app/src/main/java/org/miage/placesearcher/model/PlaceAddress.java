package org.miage.placesearcher.model;

import com.google.gson.annotations.Expose;

public class PlaceAddress {
    @Expose
    public PlaceProperties properties;

    @Expose
    public PlaceCoordinates geometry;
}