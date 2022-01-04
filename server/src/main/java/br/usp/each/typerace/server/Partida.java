package br.usp.each.typerace.server;

public class Partida {
    public String[] listaDePalavrasDaPartida;
    public boolean comecouPartida;
    public static long tempoInicio;
    public static long tempoFim;

    public Partida() {
        this.comecouPartida = false;
    }

    public void comecarPartida() {
        comecouPartida = true;
        tempoInicio = System.currentTimeMillis();
    }

    public void terminarPartida() {
        comecouPartida = false;
        tempoFim = System.currentTimeMillis();
    }

    public long tempoAteAgora() {
        return System.currentTimeMillis() - tempoInicio;
    }
}
