package br.usp.each.typerace.server;

import java.util.HashMap;
import java.util.Map;

public class Partida {
    private Map<String, Integer> competidores;
    public String[] listaDePalavrasDaPartida;
    public boolean comecouPartida;
    public static long tempoInicio;
    public static long tempoFim;

    public Partida() {
        this.competidores = new HashMap<String, Integer>();
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

    public long tempoAteAgora() {
        return System.currentTimeMillis() - this.tempoInicio;
    }

    public Map<String, Competidor> getCompetidores() {
        return this.competidores;
    }

}
