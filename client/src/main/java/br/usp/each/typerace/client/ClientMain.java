package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;
    private static final Scanner sc = new Scanner(System.in);

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(Integer clientId) {
        System.out.println("Iniciando cliente: " + clientId);
        client.connect();
    }

    private static void availableCommands(){
        System.out.println("1. start      ()");
        System.out.println("2. quit      ()");
        System.out.println("3. help     ()");
    }

    public static void main(String[] args) {
        /*
           FIXME: Remover essas strings fixas
           Como podemos fazer para que o cliente receba um parâmetro indicando a qual servidor
           ele deve se conectar e o seu ID?
        */
        String removeMe = "ws://localhost:8080";
        Integer clientId = 0;

        try {
            WebSocketClient client = new Client(new URI(removeMe));

            ClientMain main = new ClientMain(client);
            main.init(clientId);

            System.out.println("Digite o comando que deseja executar");
            System.out.println("Digite 'help' para visualizar os comandos disponíveis");

            String cmd = sc.nextLine();

            switch (cmd.toLowerCase(Locale.ROOT)) {
                case "help":
                    availableCommands();
                    break;
                case "start":
                    client.send("START");
                    break;
                case "quit":
                    System.out.println("SAIU");
                    break;
                default:
                    System.out.println("Comando invalido, tente novamente!");
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
