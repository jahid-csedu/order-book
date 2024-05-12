package org.example.client;

import org.example.util.logger.CustomLogger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.function.Consumer;

public class OrderBookClient extends WebSocketClient {
    private final Consumer<String> messageHandler;

    public OrderBookClient(URI serverUri, Consumer<String> messageHandler) {
        super(serverUri);
        this.messageHandler = messageHandler;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        CustomLogger.info("Connected to Websocket API");
    }

    @Override
    public void onMessage(String message) {
        if(messageHandler != null) {
            messageHandler.accept(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        CustomLogger.info("Connection Closed");
    }

    @Override
    public void onError(Exception ex) {
        CustomLogger.error("Error occurred: "+ex.getMessage(), ex);
    }
}
