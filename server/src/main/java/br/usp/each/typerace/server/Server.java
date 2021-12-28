package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;
    private String[] listaDePalavras;
    private Partida partida;
    private static final int quantidadePalavras = 5;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Bem vindo ao servidor!"); //This method sends a message to the new client
        //broadcast( "new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        broadcast("Jogador " + handshake.getResourceDescriptor() + " entrou na partida!");
        System.out.println("Nova conexão ao cliente de endereço " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Encerra " + conn.getRemoteSocketAddress() + " com código de saída " + code + ". Informações adicionais: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Mensagem recebida de "	+ conn.getRemoteSocketAddress() + ": " + message);
        if (!partida.comecouPartida) {
            switch (message) {
                case "START":
                    iniciaJogo();
                    break;
                case "QUIT":
                    desconectaJogador(conn);
                    break;
                default:
                    break;
            }
        } else if (partida.comecouPartida) {
//            String playerId = getPlayerId(conn.getResourceDescriptor());
//            boolean isCorrectAnswer = trSession.verifyAnswer(message, playerId);
//            String correctAnswer = "Voce acertou!\n";
//            if (!isCorrectAnswer)
//                correctAnswer = "Voce errou.\n";
//            conn.send(correctAnswer + trSession.getPlayerAvaiableWords(playerId));
//            if (trSession.isThereAWinner())
//                endGame();
        }
    }

    public void iniciaJogo() {
        broadcast("COMEÇOU:\n ");
        listaDePalavras = iniciaLista(quantidadePalavras);
        partida.listaDePalavrasDaPartida = listaDePalavras;
        for (int i = 0; i <= listaDePalavras.length - 1; i++) {
            broadcast(listaDePalavras[i]);
        }
    }

    public String[] iniciaLista(int tamanho) {
        ListaPalavras palavras = new ListaPalavras();
        return palavras.iniciaLista(tamanho);
    }

    private void desconectaJogador(WebSocket conn) {
//        String playerId = getPlayerId(conn.getResourceDescriptor());
//        connections.remove(playerId);
//        trSession.removePlayerFromSessionById(playerId);
//        conn.close();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override
    public void onStart() {
        System.out.println("Servidor iniciado com sucesso.");
    }
}
