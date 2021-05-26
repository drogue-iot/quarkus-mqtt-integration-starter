package io.drogue.iot.demo.data;

import java.time.Instant;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * An incoming device message.
 */
@RegisterForReflection
public class DeviceEvent {
    private String deviceId;
    private Instant timestamp;
    private String payload;

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

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return this.payload;
    }
}
