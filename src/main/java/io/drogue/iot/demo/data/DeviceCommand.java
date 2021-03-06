package io.drogue.iot.demo.data;

import java.util.Arrays;
import java.util.StringJoiner;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * An outgoing device message.
 */
@RegisterForReflection
public class DeviceCommand {
    private String deviceId;
    private String command;
    private byte[] payload;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DeviceCommand.class.getSimpleName() + "[", "]")
                .add("deviceId='" + this.deviceId + "'")
                .add("command='" + this.command + "'")
                .add("payload=" + Arrays.toString(this.payload))
                .toString();
    }
}
