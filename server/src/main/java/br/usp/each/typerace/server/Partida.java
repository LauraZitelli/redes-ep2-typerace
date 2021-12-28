package br.usp.each.typerace.server;

import java.util.Map;
import java.util.Set;

public class Partida {
    private Map<String, Competidor> competidores;
    private Set<String> words;
    private boolean gameStarted;
    private static final int maxScore = 3;
    private static long startGameTime;
    private static long endGameTime;
}
