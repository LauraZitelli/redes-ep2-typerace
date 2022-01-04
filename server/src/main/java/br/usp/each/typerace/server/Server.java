package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;
    public Map<String, Integer> competidores = new HashMap<String, Integer>();
    public Map<String, String[]> listaPalavrasCompetidor = new HashMap<String, String[]>();
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
            conn.send("Este nome já existe. Escolha um novo nickname!");
            conn.close(4000, "Nickname já existe");
        } else {
            conn.send("Bem vindo ao servidor!\n");
            connections.put(idCliente, conn);
            competidores.put(idCliente, 0);

            broadcast("Jogador " + idCliente + " entrou na partida!\n");
            if(connections.size() == 1) {
                broadcast("Há " + connections.size() + " jogador na partida.\n");
            } else {
                broadcast("Há " + connections.size() + " jogadores na partida.\n");
            }
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
        System.out.println("4. -p");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
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
        partida.comecarPartida();
        partida.listaDePalavrasDaPartida = iniciaLista(quantidadePalavras);

        for (int i = 0; i < partida.listaDePalavrasDaPartida.length; i++) {
            broadcast(partida.listaDePalavrasDaPartida[i]);
        }
    }

    public void avaliacao(String idCliente, String mensagem) {
        if(!listaPalavrasCompetidor.containsKey(idCliente)) {
            listaPalavrasCompetidor.put(idCliente, partida.listaDePalavrasDaPartida);
        }
        if(competidores.get(idCliente) == quantidadeAcertos) {
            partida.terminarPartida();
            broadcast("\n\nPartida finalizada\n" + idCliente + " VENCEU!!!!!\n");
            mostraPlacar(competidores);
            return;
        }
        for (int i = 0; i <= (listaPalavrasCompetidor.get(idCliente).length) - 1; i++) {
            if(listaPalavrasCompetidor.get(idCliente)[i].equals(mensagem)) {
                adicionaPontos(idCliente);
                removePalavra(idCliente, mensagem);
            } else if (mensagem.equals("-p")) {
                mostraPlacar(competidores);
                break;
            }
        }
        System.out.println("Pontos do jogador " + idCliente + " :" + competidores.get(idCliente));
    }

    public void adicionaPontos(String idCliente) {
        Integer count = competidores.get(idCliente);
        competidores.put(idCliente, count + 1);
    }

    public void removePalavra(String idCliente, String mensagem) {
        String[] listaDoCompetidor = listaPalavrasCompetidor.get(idCliente);
        List<String> list = new ArrayList<String>(Arrays.asList(listaDoCompetidor));
        list.remove(mensagem);
        listaDoCompetidor = list.toArray(new String[0]);

        listaPalavrasCompetidor.put(idCliente, listaDoCompetidor);
    }

    public String[] iniciaLista(int tamanho) {
        ListaPalavras palavras = new ListaPalavras();
        return palavras.iniciaLista(tamanho);
    }

    public void mostraPlacar(Map<String, Integer> competidores) {
         Placar placar = new Placar(this.partida, competidores, quantidadePalavras);
         imprimirRankingEDuracao(quantidadePalavras, this.partida, placar);
    }

    public void imprimirRankingEDuracao(int numeroPalavras, Partida partida, Placar placar) {
        broadcast("Ranking: ");
        for (Map.Entry<String, Integer> entrada : placar.getColocacao().entrySet()) {
            broadcast("Jogador: " +  entrada.getKey()
                    + " Acertos: " + entrada.getValue() + " Erros: " + (numeroPalavras - entrada.getValue()));
        }
        broadcast("Duração: " + partida.tempoAteAgora()/1000 + "s");
    }

    private void desconectaJogador(WebSocket conn, String idCliente) {
        connections.remove(idCliente);
        listaPalavrasCompetidor.remove(idCliente);
        competidores.remove(idCliente);

        System.out.println("ID Cliente " + idCliente);
        conn.close();
        broadcast("Jogador " + idCliente + " abandonou a partida.\n");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println(ex);
    }

    @Override
    public void onStart() {
        System.out.println("Servidor iniciado com sucesso.");
    }
}
