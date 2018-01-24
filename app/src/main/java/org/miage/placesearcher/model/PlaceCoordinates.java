package org.miage.placesearcher.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexmorel on 24/01/2018.
 */

public class PlaceCoordinates {

    @Expose
    List<Double> coordinates = new ArrayList<>();

    public Double getLatitude() {
        if (coordinates.size() >= 2) {
            return coordinates.get(1);
        }
        return 0d;
    }

    public Double getLongitude() {
        if (coordinates.size() >= 2) {
            return coordinates.get(0);
        }
        return 0d;
    }
}
