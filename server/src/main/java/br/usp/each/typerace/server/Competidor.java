package br.usp.each.typerace.server;

import java.util.Set;

public class Competidor {
    private String id;
    private int acertos;
    private int erros;
    private Set<String> words;

    public Competidor(String id) {
        this.id = id;
        this.acertos = 0;
        this.erros = 0;
    }

    public String getId() {
        return id;
    }

}
