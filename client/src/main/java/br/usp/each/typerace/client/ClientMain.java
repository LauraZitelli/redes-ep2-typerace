package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;
    private static final Scanner sc = new Scanner(System.in);

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String clientId) {
        System.out.println("Iniciando cliente: " + clientId);
        client.connect();
    }

    public static void main(String[] args) {
        /*
           FIXME: Remover essas strings fixas
           Como podemos fazer para que o cliente receba um parâmetro indicando a qual servidor
           ele deve se conectar e o seu ID?
        */
        String url = "ws://localhost:8080";
        String nome;

        try {
            System.out.println("Qual é o seu nickname?");
            nome = sc.nextLine();

            if (nome.isEmpty()) {
                System.out.println("Nickname vazio. Informe um nick válido.");
            }

            WebSocketClient client = new Client(new URI(url + "/" + nome));
            ClientMain main = new ClientMain(client);
            main.init(nome);

            while(true) {
                String texto = sc.nextLine();
                if(texto.length() > 0)
                {
                    client.send(texto);
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
