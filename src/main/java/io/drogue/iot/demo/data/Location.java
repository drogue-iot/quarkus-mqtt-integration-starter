package io.drogue.iot.demo.data;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Location {
    double lat;
    double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
