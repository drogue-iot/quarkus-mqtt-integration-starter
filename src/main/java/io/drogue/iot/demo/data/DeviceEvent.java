package io.drogue.iot.demo.data;

import java.time.Instant;
import java.util.StringJoiner;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * An incoming device message.
 */
@RegisterForReflection
public class DeviceEvent {
    private String deviceId;
    private Instant timestamp;
    private double temperature;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return this.temperature;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DeviceEvent.class.getSimpleName() + "[", "]")
                .add("deviceId='" + deviceId + "'")
                .add("timestamp=" + timestamp)
                .add("temperature=" + temperature)
                .toString();
    }
}
