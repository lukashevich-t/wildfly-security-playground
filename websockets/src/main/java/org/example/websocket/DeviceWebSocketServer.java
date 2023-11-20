package org.example.websocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.example.model.Device;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {

    private static final Logger log = Logger.getLogger(DeviceWebSocketServer.class.getName());

    @Inject
    private DeviceSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        log.log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        int id;
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            switch (jsonMessage.getString("action")) {
                case "add":
                    Device device = new Device();
                    device.setName(jsonMessage.getString("name"));
                    device.setDescription(jsonMessage.getString("description"));
                    device.setType(jsonMessage.getString("type"));
                    device.setStatus("Off");
                    sessionHandler.addDevice(device);
                    break;
                case "remove":
                    id = jsonMessage.getInt("id");
                    sessionHandler.removeDevice(id);
                    break;
                case "toggle":
                    id = jsonMessage.getInt("id");
                    sessionHandler.toggleDevice(id);
                    break;
                case "ping":
                    log.severe("ping");
                    break;
            }
        }
    }
}