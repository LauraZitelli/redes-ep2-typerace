package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("new connection opened");
    }

    @Override
    public void onMessage(String message) {
        if(message.contains("COMEÇOU") || message.contains("Bem vindo ao servidor!") || message.contains("Jogador")) {
            System.out.println(message);
        } else {
            System.out.println("Digite a palavra: " + message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Server closed with exit code " + code);
    }

    @Override
    public void onError(Exception ex) {
        // TODO: Implementar
    }
}
