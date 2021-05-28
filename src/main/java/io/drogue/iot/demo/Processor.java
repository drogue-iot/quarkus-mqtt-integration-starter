package io.drogue.iot.demo;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.drogue.iot.demo.data.DeviceCommand;
import io.drogue.iot.demo.data.DeviceEvent;
import io.smallrye.reactive.messaging.annotations.Broadcast;

/**
 * Example processor, which can generate a command based on an event.
 */
@ApplicationScoped
public class Processor {
    @Incoming(Channels.TELEMETRY)
    @Outgoing(Channels.COMMAND)
    @Broadcast
    public DeviceCommand process(final DeviceEvent event) {
        return null;
    }
}
