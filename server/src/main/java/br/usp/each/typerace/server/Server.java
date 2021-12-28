package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;
    private String[] listaDePalavras;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Bem vindo ao servidor!"); //This method sends a message to the new client
        //broadcast( "new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        broadcast("Jogador " + handshake.getResourceDescriptor() + " entrou na partida!");
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
        if(message.equals("START")) {
            iniciaJogo();
        }
    }

    public void iniciaJogo() {
        broadcast("COMEÇOU:\n ");
        listaDePalavras = iniciaLista(8);
        for (int i = 0; i <= listaDePalavras.length - 1; i++) {
            broadcast(listaDePalavras[i]);
        }
    }

    public String[] iniciaLista(int tamanho) {
        ListaPalavras palavras = new ListaPalavras();
        return palavras.iniciaLista(tamanho);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }
}
