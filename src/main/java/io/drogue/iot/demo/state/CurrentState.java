package io.drogue.iot.demo.state;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.drogue.iot.demo.Channels;
import io.drogue.iot.demo.data.DeviceEvent;
import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class CurrentState {
    private static final Logger LOG = LoggerFactory.getLogger(CurrentState.class);

    private DeviceEvent lastEvent;

    @Incoming(Channels.TELEMETRY)
    public void telemetryChange(final DeviceEvent event) {
        this.lastEvent = event;
        LOG.debug("Record last event: {}", event);
    }

    public DeviceEvent getLastEvent() {
        return this.lastEvent;
    }
}
