package br.usp.each.typerace.server;

import java.util.*;
import java.util.stream.Collectors;

public class Placar {
    private Map<String, Integer> competidores;
    private List<String> nomesCompetidores = new ArrayList<String>();
    private String placar;

    public Placar(Map<String, Integer> competidores, List<String> nomesCompetidores) {
       this.competidores = competidores;
       this.nomesCompetidores = nomesCompetidores;
    }

    public void calcularPlacar() {
        Integer[] pontuacao = new Integer[competidores.size()];
        for(int i=0; i<competidores.size(); i++) {
            pontuacao[i] = (competidores.get(nomesCompetidores.get(i)));
        }
        List<Integer> listaPontuacao = new ArrayList<Integer>(Arrays.asList(pontuacao));
        List<Integer> sortedList = listaPontuacao.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        System.out.println("PLACAR: \n");
        sortedList.forEach(System.out::println);
    }

}
