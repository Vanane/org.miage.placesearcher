package org.miage.placesearcher.model;

import com.google.gson.annotations.Expose;

public class PlaceProperties {
    @Expose
    public String name;

    @Expose
    public String postcode;

    @Expose
    public String city;

    @Expose
    public String type;

    public boolean isStreet() {
        return type != null && type.equals("street");
    }
}