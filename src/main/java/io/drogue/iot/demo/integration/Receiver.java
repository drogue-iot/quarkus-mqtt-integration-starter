package io.drogue.iot.demo.integration;

import static io.cloudevents.core.CloudEventUtils.mapData;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import io.drogue.iot.demo.Channels;
import io.drogue.iot.demo.data.DeviceEvent;
import io.drogue.iot.demo.data.Payload;
import io.quarkus.runtime.Startup;
import io.smallrye.reactive.messaging.annotations.Broadcast;

/**
 * Receive events from the Drogue IoT MQTT integration.
 */
@Startup
@ApplicationScoped
public class Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Inject
    ObjectMapper objectMapper;

    /**
     * Receive an event, parse into a Cloud Event, and extract the TTN uplink information.
     *
     * @param rawMessage The raw MQTT message.
     * @return The processed {@link DeviceEvent}, or {@code null} if the event couldn't be processed.
     */
    @Incoming(Channels.DROGUE_INBOUND)
    @Outgoing(Channels.TELEMETRY)
    @Broadcast
    public DeviceEvent process(Message<byte[]> rawMessage) {

        // we always ack, as we don't care about errors in this demo

        rawMessage.ack();

        // start processing

        var format = EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE);

        if (format == null) {
            LOG.warn("Failed to resolve CloudEvent format provider");
            return null;
        }

        var message = format.deserialize(rawMessage.getPayload());

        LOG.debug("Received event: {}", message);

        var schema = message.getDataSchema();
        if (schema == null || !schema.toString().equals("urn:drogue:iot:temperature")) {
            LOG.info("Unknown data schema: {}", schema);
            return null;
        }

        LOG.info("Received message: {}", message);

        var mappedMessage = mapData(
                message,
                PojoCloudEventDataMapper.from(this.objectMapper, Payload.class)
        );
        if (mappedMessage == null || mappedMessage.getValue() == null) {
            LOG.info("Failed to map event payload");
            return null;
        }

        var payload = mappedMessage.getValue();

        // create device event

        var device = new DeviceEvent();

        var deviceId = message.getExtension("device");
        if (deviceId == null) {
            LOG.info("Missing 'device' extension");
            return null;
        }
        device.setDeviceId(deviceId.toString());

        var timestamp = message.getTime();
        if (timestamp == null) {
            LOG.info("Missing attribute 'time'");
            return null;
        }
        device.setTimestamp(timestamp.toInstant());
        device.setTemperature(payload.getTemperature());
        device.setLocation(payload.getGeoloc());

        // done

        return device;
    }

}