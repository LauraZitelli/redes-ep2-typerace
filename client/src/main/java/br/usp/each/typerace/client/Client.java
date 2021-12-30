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
        System.out.println("Conexão ao servidor iniciada.");
    }

    @Override
    public void onMessage(String message) {
        if(message.contains("COMEÇOU") || message.contains("Bem vindo ao servidor!") || message.contains("Jogador")
                || message.contains("Pontos") || message.contains("Há") || message.contains("VENCEU") || message.contains("Acertos") || message.contains("Erros") || message.contains("Partida") ||  message.contains("\n") ||  message.contains("Ranking") ||  message.contains("Duração")){
            System.out.println(message);
        } else {
            System.out.println("Digite a palavra: " + message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Servidor desconectou com o código de saída " + code);
    }

    @Override
    public void onError(Exception ex) {
        // TODO: Implementar
    }
}
