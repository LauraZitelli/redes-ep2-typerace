package br.usp.each.typerace.server;

import java.util.HashMap;
import java.util.Map;

public class Partida {
    private Map<String, Competidor> competidores;
    public String[] listaDePalavrasDaPartida;
    public boolean comecouPartida;
    public static long tempoInicio;
    public static long tempoFim;

    public Partida() {
        this.competidores = new HashMap<String, Competidor>();
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

    public long tempoTotalPartida() {
        return tempoFim - tempoInicio;
    }

}
