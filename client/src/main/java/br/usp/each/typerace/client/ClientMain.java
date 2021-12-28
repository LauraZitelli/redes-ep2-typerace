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

    public void init(String clientId) {
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
        String url = "ws://localhost:8080";
        String nome;

        try {
            WebSocketClient client = new Client(new URI(url));
            ClientMain main = new ClientMain(client);

            System.out.println("Qual é o seu nickname?");
            nome = sc.nextLine();

            if (nome.isEmpty()) {
                System.out.println("Nickname vazio. Informe um nick válido.");
            }

            main.init(nome);

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
                    client.send("QUIT");
                    break;
                default:
                    System.out.println("Comando inválido. Digite 'help' para ver a lista de comandos válidos!");
            }

            while(true) {
                String texto = sc.nextLine();
                if(texto.length() > 0)
                {
                    if(texto.equalsIgnoreCase("quit")) {
                        client.close();
                        break;
                    }
                    client.send(texto);
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
