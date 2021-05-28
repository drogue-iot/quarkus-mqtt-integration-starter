package io.drogue.iot.demo.ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.drogue.iot.demo.Channels;
import io.drogue.iot.demo.data.DeviceCommand;
import io.drogue.iot.demo.data.DeviceEvent;
import io.vertx.core.json.JsonObject;

@ServerEndpoint("/ws")
@ApplicationScoped
public class EventsResource {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private Object lastEvent;
    private Object lastCommand;

    @OnOpen
    public void onOpen(Session session) {
        if (lastEvent != null) {
            session.getAsyncRemote().sendObject(lastEvent);
        }
        if (lastCommand != null) {
            session.getAsyncRemote().sendObject(lastCommand);
        }
        sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(session.getId());
    }

    @Incoming(Channels.TELEMETRY)
    void telemetryEvent(DeviceEvent event) {
        Object nextEvent = new JsonObject()
                .put("type", "telemetry")
                .put("payload", JsonObject.mapFrom(event)).toString();
        this.lastEvent = nextEvent;
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(nextEvent);
        });
    }

    @Incoming(Channels.COMMAND)
    void commandEvent(DeviceCommand command) {
        Object nextCommand = new JsonObject()
                .put("type", "command")
                .put("payload", JsonObject.mapFrom(command)).toString();
        this.lastCommand = nextCommand;
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(nextCommand);
        });
    }
}
