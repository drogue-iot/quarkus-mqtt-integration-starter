package io.drogue.iot.demo.ui;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestSseElementType;

import io.drogue.iot.demo.Channels;
import io.drogue.iot.demo.data.DeviceCommand;
import io.drogue.iot.demo.data.DeviceEvent;
import io.drogue.iot.demo.state.CurrentState;
import io.smallrye.mutiny.Multi;

/**
 * This resource is used by the main UI entrypoint, providing a stream for the events.
 */
@Path("/sse")
public class SseResource {
    @Inject
    @Channel(Channels.TELEMETRY)
    Multi<DeviceEvent> events;

    @Inject
    @Channel(Channels.COMMAND)
    Multi<DeviceCommand> commands;

    @Inject
    CurrentState currentState;

    @GET
    @Path("/telemetry")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    public Multi<DeviceEvent> stream() {
        return Multi.createFrom()
                .item(this.currentState.getLastEvent())
                .onCompletion().switchTo(this.events);
    }

    @GET
    @Path("/commands")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    public Multi<DeviceCommand> commands() {
        return this.commands;
    }

}
