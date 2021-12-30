package br.usp.each.typerace.server;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Placar{
    private Map<String, Integer> colocacao;

    public Placar(Partida partida, Map<String, Integer> competidores, int numeroPalavras) {
        this.colocacao = ordenarPorAcertos(competidores);
    }

    private static Map<String, Integer> ordenarPorAcertos(Map<String, Integer> competidores) {
        
        List<Map.Entry<String, Integer>> lista =
                new LinkedList<Map.Entry<String, Integer>>(competidores.entrySet());

        

        Collections.sort(lista, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a,
                            Map.Entry<String, Integer> b) {
                return (b.getValue() - a.getValue());
            }
        });

        Map<String, Integer> competidoresOrdenados = new LinkedHashMap<String, Integer>();
        
        for (Map.Entry<String, Integer> entry : lista) {
            competidoresOrdenados.put(entry.getKey(), entry.getValue());
        }

        return competidoresOrdenados;

    }

    public Map<String, Integer> getColocacao() {
        return colocacao;
    }

}