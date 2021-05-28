package io.drogue.iot.demo.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Payload {
    private double temperature;
    private Location geoloc;

    @JsonProperty("temp")
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setGeoloc(Location geoloc) {
        this.geoloc = geoloc;
    }

    public Location getGeoloc() {
        return geoloc;
    }
}
