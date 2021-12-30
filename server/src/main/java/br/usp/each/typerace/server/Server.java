package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;
    public Map<String, Integer> competidores = new HashMap<String, Integer>();
    public Map<String, String[]> listaCompetidor = new HashMap<String, String[]>();
    public String idCliente;
    private final Partida partida = new Partida();
    private static final int quantidadePalavras = 8;
    private static final int quantidadeAcertos = 5;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        idCliente = conn.getResourceDescriptor().split("/")[1];
        if(connections.containsKey(idCliente)) {
            System.out.println("TE VIRA NEGO");
        } else {
            conn.send("Bem vindo ao servidor!\n"); //This method sends a message to the new client
            connections.put(idCliente, conn);
            competidores.put(idCliente, 0);
            broadcast("Jogador " + idCliente + " entrou na partida!\n");
            broadcast("Há " + connections.size() + " jogadores na partida.\n");
            System.out.println("Nova conexão ao cliente " + idCliente + " de endereço " + conn.getRemoteSocketAddress());
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Encerra " + conn.getRemoteSocketAddress() + " com código de saída " + code + ". Informações adicionais: " + reason);
    }

    private static void availableCommands(){
        System.out.println("1. start");
        System.out.println("2. quit");
        System.out.println("3. help");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Mensagem recebida de "	+ conn.getRemoteSocketAddress() + ": " + message);
        idCliente = conn.getResourceDescriptor().split("/")[1];
        if (!partida.comecouPartida) {
            switch (message.toLowerCase(Locale.ROOT)) {
                case "start":
                    iniciaJogo();
                    break;
                case "quit":
                    desconectaJogador(conn, idCliente);
                    break;
                case "help":
                    availableCommands();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Cliente " + idCliente + " enviou a mensagem: " + message);
            avaliacao(idCliente, message);
        }
    }

    public void iniciaJogo() {
        broadcast("COMEÇOU:\n ");
        partida.comecouPartida = true;
        partida.listaDePalavrasDaPartida = iniciaLista(quantidadePalavras);
        listaCompetidor.put(idCliente, partida.listaDePalavrasDaPartida);

        for (int i = 0; i < partida.listaDePalavrasDaPartida.length; i++) {
            broadcast(partida.listaDePalavrasDaPartida[i]);
        }
    }

    public void avaliacao(String idCliente, String mensagem) {
        if(competidores.get(idCliente) == quantidadeAcertos) {
            broadcast(idCliente + " VENCEU!!!!!");
            return;
        }
        for (int i = 0; i <= listaCompetidor.get(idCliente).length -1; i++) {
            if(listaCompetidor.get(idCliente)[i].contains(mensagem)) {
                adicionaPontos(idCliente);
                removePalavra(idCliente, mensagem);
            }
        }
        System.out.println("Pontos do jogador " + idCliente + " :" + competidores.get(idCliente));
    }

    public void adicionaPontos(String idCliente) {
        Integer count = competidores.get(idCliente);
        competidores.put(idCliente, count + 1);
    }

    public void removePalavra(String idCliente, String mensagem) {
        String[] listaDoCompetidor = listaCompetidor.get(idCliente);
        List<String> list = new ArrayList<String>(Arrays.asList(listaDoCompetidor));
        list.remove(mensagem);
        listaDoCompetidor = list.toArray(new String[0]);

        listaCompetidor.put(idCliente, listaDoCompetidor);
        for(int i=0; i<= listaCompetidor.get(idCliente).length-1; i++) {
            System.out.println("Lista Atualizada do jogador " + idCliente + " :" + listaCompetidor.get(idCliente)[i]);
        }
    }

    public String[] iniciaLista(int tamanho) {
        ListaPalavras palavras = new ListaPalavras();
        return palavras.iniciaLista(tamanho);
    }

    public void mostraPlacar(Map<String, Integer> competidores) {
         Placar placar = new Placar(this.partida, competidores, quantidadePalavras);
         int numeroPalavras = quantidadePalavras;
         placar.imprimirRankingEDuracao(numeroPalavras, partida);
    }

    private void desconectaJogador(WebSocket conn, String idCliente) {
        connections.remove(idCliente);
//        trSession.removePlayerFromSessionById(playerId);
        System.out.println("ID Cliente " + idCliente);
        conn.close();
        broadcast("Jogador " + idCliente + " abandonou a partida.\n");
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
