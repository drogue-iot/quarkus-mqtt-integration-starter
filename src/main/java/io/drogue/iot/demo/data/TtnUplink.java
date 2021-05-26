package io.drogue.iot.demo.data;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * An uplink message, coming the The Things Network.
 * <br>
 * This class doesn't contain all the field, just the ones we are interested in.
 */
@RegisterForReflection
public class TtnUplink {

    public static class Message {
        @JsonProperty("f_port")
        int port;

        @JsonProperty("frm_payload")
        byte[] payload;

        @JsonProperty("received_at")
        private Instant receivedAt;

        public void setPort(int port) {
            this.port = port;
        }

        public int getPort() {
            return this.port;
        }

        public byte[] getPayload() {
            return this.payload;
        }

        public void setPayload(byte[] payload) {
            this.payload = payload;
        }

        public Instant getReceivedAt() {
            return this.receivedAt;
        }

        public void setReceivedAt(Instant receivedAt) {
            this.receivedAt = receivedAt;
        }
    }

    @JsonProperty("uplink_message")
    private Message uplinkMessage;

    public void setUplinkMessage(Message uplinkMessage) {
        this.uplinkMessage = uplinkMessage;
    }

    public Message getUplinkMessage() {
        return this.uplinkMessage;
    }
}
